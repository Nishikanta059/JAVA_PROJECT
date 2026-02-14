package com.nishi.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "BATCHES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Batch {

    @EmbeddedId
    BatchIdCK batchIdCK;


    BigDecimal quantity;

    BigDecimal reservedQuantity;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    Date expiryDate;
}
