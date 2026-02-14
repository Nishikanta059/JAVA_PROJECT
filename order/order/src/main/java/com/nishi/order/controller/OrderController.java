package com.nishi.order.controller;


import com.nishi.order.controller.entity.OrderRequest;
import com.nishi.order.factory.ResponseFactory;
import com.nishi.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping()
        public class OrderController {


            @Autowired
            ResponseFactory responseFactory;

            @Autowired
            OrderService orderService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "order-service");
        response.put("timestamp", new Date());

        return ResponseEntity.ok(response);
    }


            @PostMapping("/order")
            public ResponseEntity updateProductInventoryDetails(@RequestBody OrderRequest orderRequest){

             return responseFactory.buildResponse("order",orderService.createOrder(orderRequest));

            }

        }
