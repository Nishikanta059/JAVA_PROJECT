package com.nishi.inventory.factory;

import com.nishi.inventory.controller.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ResponseFactory {


    final static String MESSAGE = "message";

    final static String STATUS = "status";

    public static ResponseEntity buildResponse(String type, Map dataMap) {

        if (dataMap == null || dataMap.isEmpty()) {
            ApiResponse response = ApiResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Product Data not found")
                    .data(null)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (type.equals("order")) {
            List<String> batchIds =
                    (List<String>) dataMap.getOrDefault("reservedFromBatchIds", List.of());

            if (batchIds.isEmpty()) {
                dataMap.put(MESSAGE, "Product quantity is not available in Inventory");
                dataMap.put(STATUS, "FAILED");
            } else {
                dataMap.put(MESSAGE, "Order placed. Inventory reserved");
                dataMap.put(STATUS, "PLACED");
            }

            return ResponseEntity.ok(dataMap);
        } else {

            return ResponseEntity.ok(dataMap);
        }
    }

}
