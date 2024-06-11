package br.com.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@Entity
@Table(name = "order_oms")
public class Order  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private BigDecimal totalValue;
    private String channel;
    private String paymentStatus;
}
