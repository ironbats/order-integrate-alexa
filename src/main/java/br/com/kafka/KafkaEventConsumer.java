package br.com.kafka;

import br.com.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @KafkaListener(topics = "order-events", groupId = "order-consumer-os")
    public void consumerOrders(OrderDTO orderDTO) {
        LOG.info("Kafka Consumer Order: {} ", orderDTO);
    }
}
