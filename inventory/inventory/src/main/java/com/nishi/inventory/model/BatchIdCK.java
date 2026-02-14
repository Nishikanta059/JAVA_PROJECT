package com.nishi.inventory.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BatchIdCK implements Serializable {

    String batchId;
    String productId;
}
