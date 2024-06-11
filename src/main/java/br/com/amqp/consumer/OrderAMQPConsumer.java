package br.com.amqp.consumer;


import br.com.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderAMQPConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(OrderAMQPConsumer.class);

    @RabbitListener(queues = "order-events")
    public void consumerOrderAMQP(OrderDTO orderDTO) {
        LOG.info("AMQP Consumer events order: {}", orderDTO.toString());
    }


}
