package com.nishi.inventory.controller;

import com.nishi.inventory.controller.entity.ApiResponse;
import com.nishi.inventory.controller.entity.InventoryUpdateRequest;
import com.nishi.inventory.factory.ResponseFactory;
import com.nishi.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Autowired
    InventoryService inventoryService;

    @Autowired
    ResponseFactory responseFactory;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Inventory-service");
        response.put("timestamp", new Date());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{productId}")
    ResponseEntity<ApiResponse> getProductDetails(@PathVariable String productId){

        return responseFactory.buildResponse("product",inventoryService.getProductDetailsFromId(productId));
    }


    @PostMapping("/update")
    ResponseEntity updateProductInventoryDetails(@RequestBody InventoryUpdateRequest inventoryUpdateRequest){

        return responseFactory.buildResponse("order",inventoryService.reserveInventory(inventoryUpdateRequest.getProductId(),inventoryUpdateRequest.getQuantity()));

    }

}
