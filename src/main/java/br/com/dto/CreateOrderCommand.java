package br.com.dto;

import java.math.BigDecimal;

public record CreateOrderCommand(String orderId, BigDecimal orderValue, String channel, String paymentStatus, String createdAt, String updateAt) {

    public static CreateOrderCommand with(String orderId, BigDecimal orderValue, String channel, String paymentStatus, String createdAt, String updateAt) {
        return new CreateOrderCommand(orderId,orderValue,channel,paymentStatus,createdAt,updateAt);
    }
}