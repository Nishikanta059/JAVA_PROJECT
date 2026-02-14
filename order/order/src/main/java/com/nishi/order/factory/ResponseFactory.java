package com.nishi.order.factory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ResponseFactory {


    public static ResponseEntity buildResponse(String type, Map dataMap) {

        if (dataMap == null || dataMap.isEmpty()) {


            return new ResponseEntity<>("Something went wrong.contact nishi", HttpStatus.BAD_GATEWAY);
        }

        if (type.equals("order")) {
            List<String> batchIds =
                    (List<String>) dataMap.getOrDefault("reservedFromBatchIds", List.of());

            if (batchIds.isEmpty()) {
                return ResponseEntity.ok(dataMap);
            } else {
                return ResponseEntity.ok(dataMap);
            }

        } else {

            return ResponseEntity.ok(dataMap);
        }
    }

}
