package br.com.decision;

import lombok.Getter;

@Getter
public enum Broker {
    KAFKA("Kafka"),
    RABBITMQ("RABBIT M KILL");

    private final String displayName;

    Broker(String displayName) {
        this.displayName = displayName;
    }

}
