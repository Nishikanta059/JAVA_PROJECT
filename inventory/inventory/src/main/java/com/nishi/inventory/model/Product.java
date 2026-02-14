package com.nishi.inventory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class  Product {

   @Id
   protected String productId;

   protected String productName;
}
