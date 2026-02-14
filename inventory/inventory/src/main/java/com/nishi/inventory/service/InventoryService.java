package com.nishi.inventory.service;


import com.nishi.inventory.dto.BatchDTO;
import com.nishi.inventory.model.Batch;
import com.nishi.inventory.model.Product;
import com.nishi.inventory.repository.BatchRepository;
import com.nishi.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InventoryService {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    BatchRepository batchRepository;


    public Map getProductDetailsFromId(String productId) {

            Optional<Product> product = productRepository.findById(productId);

            if(!product.isPresent())
                return  null;


            List<BatchDTO> batches =
                    batchRepository.findBatchesByProductId(productId);

            Map<String, Object> response = new HashMap<>();

            if(batches.size()>0) {
                response.put("productId", product.get().getProductId());
                response.put("productName", product.get().getProductName());
                response.put("batches", batches);
            }


        return response;
    }


    public Map reserveInventory(String productId, int quantity) {

        Map<String, Object> response = new HashMap<>();

        Optional<Product> product = productRepository.findById(productId);

        if(!product.isPresent())
            return  null;

        List<Batch> batches = batchRepository.findAllByProduct_ProductIdOrderByExpiryDateDesc(productId);

        int requiredQuantity = quantity;
        List<String> reservedBatchIds = new ArrayList<>();


        for(Batch batch : batches){

            int remainingInventory =batch.getQuantity().subtract(batch.getReservedQuantity()).intValue();

            if(remainingInventory<=0)
                continue;

            if(remainingInventory <= requiredQuantity)
            {
                requiredQuantity-=remainingInventory;
                batch.setReservedQuantity(batch.getReservedQuantity().add(new BigDecimal(remainingInventory)));
            }else{

                batch.setReservedQuantity(batch.getReservedQuantity().add(new BigDecimal(requiredQuantity)));
                requiredQuantity=0;
            }

            reservedBatchIds.add(batch.getBatchIdCK().getBatchId());

            System.out.println(batch.getReservedQuantity());

            if(requiredQuantity<=0)
                break;

        }

        if(reservedBatchIds.size()>0 && requiredQuantity<=0) {
            System.out.println("Savinggg");
            batchRepository.saveAll(batches);
            response.put("reservedFromBatchIds",reservedBatchIds);
        }else{
            response.put("reservedFromBatchIds",new ArrayList<String>());
        }


            response.put("productName", product.get().getProductName());
            response.put("productId", product.get().getProductId());
            response.put("quantity",quantity);



        return response;
    }
}
