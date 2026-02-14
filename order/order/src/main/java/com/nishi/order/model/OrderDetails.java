package com.nishi.order.model;


import com.nishi.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ORDER_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {

    @Id
    String orderId ;

    String productId ;

    String productName ;

    BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    Date orderDate;
}
