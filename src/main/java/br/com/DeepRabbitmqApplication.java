package br.com;

import br.com.dto.OrderDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@EnableRabbit
@ComponentScan(basePackages = {"br.com", "br.com.amqp"})
public class DeepRabbitmqApplication {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public static void main(String[] args) {
        SpringApplication.run(DeepRabbitmqApplication.class, args);
    }


//    @PostConstruct
//    public void testConnectionRabbit() {
//
//        for (int i = 2; i < 1000000; i++) {
//
//            OrderDTO orderDTO = new OrderDTO();
//            orderDTO.setId(UUID.randomUUID().toString());
//            orderDTO.setChannel(randomChannel());
//            orderDTO.setPaymentStatus(randomPaymentStatus());
//            orderDTO.setTotalValue(BigDecimal.valueOf(5 * i));
//
//            rabbitTemplate.convertAndSend("order-events", orderDTO);
//        }
//
//    }
//
//    private String randomPaymentStatus() {
//        final String[] randomPaymentStatus = {"PAID", "UNPAID", "FRAUD", "UNAUTHORIZED"};
//        Random random = new Random();
//        final int choice = random.nextInt(randomPaymentStatus.length);
//        return randomPaymentStatus[choice];
//    }
//
//    private String randomChannel() {
//        final String[] randomChannel = {"APP", "WEB", "TERMINAL", "PDV"};
//        Random random = new Random();
//        final int choice = random.nextInt(randomChannel.length);
//        return randomChannel[choice];
//    }

}
