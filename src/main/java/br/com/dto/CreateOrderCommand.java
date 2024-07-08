package br.com.dto;

import java.math.BigDecimal;

public record CreateOrderCommand(String orderId, BigDecimal orderValue, String channel, String paymentStatus) {

    public static CreateOrderCommand with(String orderId, BigDecimal orderValue, String channel, String paymentStatus) {
        return new CreateOrderCommand(orderId,orderValue,channel,paymentStatus);
    }
}