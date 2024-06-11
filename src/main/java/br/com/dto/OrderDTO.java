package br.com.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class OrderDTO implements Serializable {

    private String id;
    private BigDecimal totalValue;
    private String channel;
    private String paymentStatus;
}
