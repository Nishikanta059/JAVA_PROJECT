package com.nishi.inventory.repository;

import com.nishi.inventory.model.ElectronicProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectronicProductRepository extends JpaRepository<ElectronicProduct,String> {
}
