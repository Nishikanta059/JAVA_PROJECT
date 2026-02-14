package com.nishi.inventory.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ELECTRONIC_PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectronicProduct extends Product {

   private String warranty;


}
