package br.com.decision;

import br.com.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecisionMaker {


    public Broker decision(List<OrderDTO> orderDTO) {

        boolean highThroughput = true;
        boolean highDurability = true;
        boolean simpleConfiguration = false;
        boolean streamProcessing = true;
        boolean multiProtocolSupport = false;
        int numberOfOrders = orderDTO.size();

        return decisionBroker(highThroughput, highDurability, simpleConfiguration, streamProcessing, multiProtocolSupport,numberOfOrders);
    }

    private Broker decisionBroker(boolean highThroughput,
                                  boolean highDurability,
                                  boolean simpleConfiguration,
                                  boolean streamProcessing,
                                  boolean multiProtocolSupport,
                                  int numberOfOrders) {

        int kafkaScore = 0;
        int rabbitMqScore = 0;

        // High Throughput
        if (highThroughput) {
            kafkaScore += 2;
        } else {
            rabbitMqScore += 1;
        }

        // High Durability
        if (highDurability) {
            kafkaScore += 2;
        } else {
            rabbitMqScore += 1;
        }

        // Simple Configuration
        if (simpleConfiguration) {
            rabbitMqScore += 2;
        } else {
            kafkaScore += 1;
        }

        // Stream Processing
        if (streamProcessing) {
            kafkaScore += 2;
        } else {
            rabbitMqScore += 1;
        }

        // Multi-Protocol Support
        if (multiProtocolSupport) {
            rabbitMqScore += 2;
        } else {
            kafkaScore += 1;
        }

        // Number of Orders
        if (numberOfOrders > 10000) {
            kafkaScore += 2;
        } else {
            rabbitMqScore += 10;
        }

        return kafkaScore > rabbitMqScore ? Broker.KAFKA : Broker.RABBITMQ;

    }

}
