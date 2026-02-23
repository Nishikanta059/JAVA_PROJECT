package com.nishi.inventory.repository;

import com.nishi.inventory.dto.BatchDTO;
import com.nishi.inventory.model.Batch;
import com.nishi.inventory.model.BatchIdCK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, BatchIdCK> {

    List<Batch> findAllByProduct_ProductIdOrderByExpiryDateDesc(String productId);

    @Query(" SELECT new com.nishi.inventory.dto.BatchDTO( b.batchIdCK.batchId, b.quantity, b.reservedQuantity, b.expiryDate ) FROM Batch b WHERE b.product.productId = :productId")
    List<BatchDTO> findBatchesByProductId(@Param("productId") String productId);

}
