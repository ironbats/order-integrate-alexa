package br.com.decision;

import br.com.dto.OrderDTO;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Service
public class DecisionMaker {


    public Broker decision(List<OrderDTO> orderDTO) {

        Random random = new Random();

        boolean highThroughput = random.nextBoolean();
        boolean highDurability = random.nextBoolean();
        boolean simpleConfiguration = random.nextBoolean();
        boolean streamProcessing = random.nextBoolean();
        boolean multiProtocolSupport = random.nextBoolean();
        int numberOfOrders = orderDTO.size();

        return decisionBroker(highThroughput, highDurability, simpleConfiguration, streamProcessing, multiProtocolSupport, numberOfOrders);
    }

    private Broker decisionBroker(boolean highThroughput,
                                  boolean highDurability,
                                  boolean simpleConfiguration,
                                  boolean streamProcessing,
                                  boolean multiProtocolSupport,
                                  int numberOfOrders) {

        List<Criterion> criteria = Arrays.asList(
                new Criterion(v -> highThroughput, 2, 1),
                new Criterion(v -> highDurability, 2, 1),
                new Criterion(v -> simpleConfiguration, 1, 2),
                new Criterion(v -> streamProcessing, 2, 1),
                new Criterion(v -> multiProtocolSupport, 1, 2),
                new Criterion(v -> numberOfOrders < 10000, 3, 7)
        );

        int kafkaScore = criteria.stream()
                .mapToInt(c -> c.test() ? c.getKafkaScore() : 0)
                .sum();

        int rabbitMqScore = criteria.stream()
                .mapToInt(c -> c.test() ? c.getRabbitMqScore() : 0)
                .sum();

        return kafkaScore > rabbitMqScore ? Broker.KAFKA : Broker.RABBITMQ;

    }

}

record Criterion(Predicate<Void> condition, @Getter int kafkaScore, @Getter int rabbitMqScore) {

    public boolean test() {
        return condition.test(null);
    }
}
