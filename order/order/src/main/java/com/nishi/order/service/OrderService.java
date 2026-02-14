package com.nishi.order.service;

import com.nishi.order.controller.entity.OrderRequest;
import com.nishi.order.enums.OrderStatus;
import com.nishi.order.model.OrderDetails;
import com.nishi.order.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderDetailsRepository orderDetailsRepository;



    @Autowired
    private RestTemplate restTemplate;


    @Value("${inventory.update.url}")
    private String inventoryUpdateUrl;


    final static String ORDER_ID="orderId";

    public Map<String, Object> createOrder(OrderRequest orderRequest) {

        String orderId = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 5)
                .toUpperCase();


        OrderDetails order = OrderDetails.builder()
                .orderId(orderId)
                .productId(orderRequest.getProductId())
                .quantity(new BigDecimal(orderRequest.getQuantity()))
                .orderStatus(OrderStatus.INITIATED)
                .orderDate(new Date())
                .build();

        orderDetailsRepository.save(order);

        Map<String, Object> responseMap = new HashMap<>();

        try {

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            inventoryUpdateUrl,
                            orderRequest,
                            Map.class
                    );

            System.out.println("HTTP Status: " + response.getStatusCode());

            if (!response.getStatusCode().is2xxSuccessful()) {
                order.setOrderStatus(OrderStatus.FAILED);
            } else {

                Map<String, Object> body = response.getBody();

                if (body != null) {

                    String inventoryStatus = (String) body.get("status");

                    if ("SUCCESS".equalsIgnoreCase(inventoryStatus)) {
                        order.setOrderStatus(OrderStatus.SUCCESS);
                    } else {
                        order.setOrderStatus(OrderStatus.FAILED);
                    }

                    responseMap.putAll(body);
                } else {
                    order.setOrderStatus(OrderStatus.FAILED);
                }
            }

        } catch (Exception ex) {

            System.out.println("Error calling inventory: " + ex.getMessage());
            order.setOrderStatus(OrderStatus.FAILED);
            responseMap.put("message", "Inventory service unavailable");
            responseMap.put("status", "FAILED");
        }

        orderDetailsRepository.save(order);

        responseMap.put("orderId", orderId);

        return responseMap;
    }

}
