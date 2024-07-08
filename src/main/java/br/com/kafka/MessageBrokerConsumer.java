package br.com.kafka;

import br.com.dto.OrderDTO;
import br.com.model.Order;
import br.com.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageBrokerConsumer {

    @Autowired
    private OrderRepository orderRepository;

    private static final Logger LOG = LoggerFactory.getLogger(MessageBrokerConsumer.class);

//    @KafkaListener(topics = "order-events", groupId = "order-consumer-os")
//    public void consumerOrders(OrderDTO orderDTO) {
//        LOG.info("Kafka Consumer Order: {} ", orderDTO.toString());
//
//        try {
//            Order order = new Order();
//            order.setChannel(orderDTO.getChannel());
//            order.setPaymentStatus(orderDTO.getPaymentStatus());
//            order.setTotalValue(orderDTO.getTotalValue());
//
//            orderRepository.save(order);
//        } catch (Exception cause) {
//            getError(orderDTO);
//        }
//    }

    private static void getError(OrderDTO orderDTO) {
        LOG.error("Nao foi possivel salvar o  pedido: {}", orderDTO.getId());
    }

    @RabbitListener(queues = "order-events")
    public void consumerOrderAMQP(OrderDTO orderDTO) {
        LOG.info("AMQP Consumer events order: {}", orderDTO.toString());


        try {
            Order order = new Order();
            order.setChannel(orderDTO.getChannel());
            order.setPaymentStatus(orderDTO.getPaymentStatus());
            order.setOrderValue(orderDTO.getTotalValue());

            orderRepository.save(order);
        } catch (Exception cause) {
            getError(orderDTO);
        }


    }
}
