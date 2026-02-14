package com.nishi.inventory.repository;

import com.nishi.inventory.model.ElectronicProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectronicProductRepository extends JpaRepository<ElectronicProduct,String> {
}
