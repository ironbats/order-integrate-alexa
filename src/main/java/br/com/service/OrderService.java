package br.com.service;

import br.com.decision.Broker;
import br.com.decision.DecisionMaker;
import br.com.dto.CreateOrderCommand;
import br.com.dto.OrderDTO;
import br.com.dto.response.AlexaResponse;
import br.com.dto.response.OutputSpeech;
import br.com.dto.response.Response;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DecisionMaker decisionMaker;
    @Autowired
    private KafkaTemplate<String, CreateOrderCommand> kafkaTemplate;


    public AlexaResponse createOrder() {

        Random random = new Random();
        int totalNumbersRandom = random.nextInt(199);

        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (int i = 2; i < totalNumbersRandom * 100; i++) {

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(UUID.randomUUID().toString());
            orderDTO.setChannel(randomChannel());
            orderDTO.setPaymentStatus(randomPaymentStatus());
            orderDTO.setTotalValue(BigDecimal.valueOf(5 * i));
            orderDTOS.add(orderDTO);
        }



        Broker broker = decisionMaker.decision(orderDTOS);
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        final String today = day + "/" + month + "/" + year;
        final String textForAlexa = "O total de pedidos fechados HOJE no dia : " + today + " foi de ";
        final String textForAlexaContinue = " foi utilizado para consumir essas mensagens de acordo com o algoritmo de decisão.";


        List<OrderDTO> ordersWithPaymentOk = orderDTOS.stream().filter(order -> order.getPaymentStatus().equals("PAID")).toList();
        List<OrderDTO> ordersWithPaymentNOk = orderDTOS.stream().filter(order -> order.getPaymentStatus().equals("UNPAID")).toList();;
        List<OrderDTO> ordersWithPaymentFraud = orderDTOS.stream().filter(order -> order.getPaymentStatus().equals("FRAUD")).toList();;

        final String textForAlexaPaymentOk = " Verifiquei também que voce tem: "
                + ordersWithPaymentOk.size() + " pedidos com pagamento confirmado, "
                + ordersWithPaymentNOk.size() + " pedidos com pagamentos não confirmados  e "
                + ordersWithPaymentFraud.size() + "  pedidos com fraude.";

        OutputSpeech outputSpeech = new
                OutputSpeech("PlainText", textForAlexa +
                orderDTOS.size() + " E o "
                 + broker.getDisplayName() + textForAlexaContinue + textForAlexaPaymentOk);

        Response response = new Response(outputSpeech, true);
        AlexaResponse alexaResponse = new AlexaResponse("1.0", response);

        //if (broker.name().equals(Broker.KAFKA.name())) {
            for (OrderDTO order : orderDTOS) {
                CreateOrderCommand command = new CreateOrderCommand(order.getId(),order.getTotalValue(), order.getChannel(),order.getPaymentStatus());
                CompletableFuture<SendResult<String, CreateOrderCommand>> future = kafkaTemplate.send("order-events", command);
                future.whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Message sent: {}" + result.getRecordMetadata().offset());
                    } else {
                        System.out.println("Failed to send message: " + ex.getMessage());
                    }
                });
            }

        //} else {
//            for (OrderDTO orderDTO : orderDTOS) {
//                rabbitTemplate.convertAndSend("order-events", orderDTO);
//            }
       // }

        return alexaResponse;
    }

    private String randomPaymentStatus() {
        final String[] randomPaymentStatus = {"PAID", "UNPAID", "FRAUD"};
        Random random = new Random();
        final int choice = random.nextInt(randomPaymentStatus.length);
        return randomPaymentStatus[choice];
    }

    private String randomChannel() {
        final String[] randomChannel = {"APP", "WEB", "TERMINAL", "PDV"};
        Random random = new Random();
        final int choice = random.nextInt(randomChannel.length);
        return randomChannel[choice];
    }

}
