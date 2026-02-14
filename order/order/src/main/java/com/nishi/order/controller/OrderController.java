package com.nishi.order.controller;


import com.nishi.order.controller.entity.OrderRequest;
import com.nishi.order.factory.ResponseFactory;
import com.nishi.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping()
        public class OrderController {


            @Autowired
            ResponseFactory responseFactory;

            @Autowired
            OrderService orderService;


            @PostMapping("/order")
            ResponseEntity updateProductInventoryDetails(@RequestBody OrderRequest orderRequest){

             return responseFactory.buildResponse("order",orderService.createOrder(orderRequest));

            }

        }
