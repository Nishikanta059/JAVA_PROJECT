package com.nishi.order;

import com.nishi.order.controller.entity.OrderRequest;
import com.nishi.order.repository.OrderDetailsRepository;
import com.nishi.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;





@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderDetailsRepository orderDetailsRepository;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(orderService, "inventoryUpdateUrl", "http://test-url");
    }

    @Test
    void shouldCreateOrderSuccessfully() {

        OrderRequest request = new OrderRequest();
        request.setProductId("1001");
        request.setQuantity(5);

        Map<String, Object> inventoryResponse = new HashMap<>();
        inventoryResponse.put("status", "SUCCESS");
        inventoryResponse.put("message", "Reserved");

        ResponseEntity<Map> responseEntity =
                ResponseEntity.ok(inventoryResponse);

        when(restTemplate.postForEntity(
                anyString(),
                any(),
                eq(Map.class)
        )).thenReturn(responseEntity);

        Map<String, Object> result = orderService.createOrder(request);

        assertEquals("SUCCESS", result.get("status"));
        assertNotNull(result.get("orderId"));

        verify(orderDetailsRepository, times(2)).save(any());
    }


    @Test
    void shouldMarkOrderFailedWhenInventoryFails() {

        OrderRequest request = new OrderRequest();
        request.setProductId("1001");
        request.setQuantity(5);

        Map<String, Object> inventoryResponse = new HashMap<>();
        inventoryResponse.put("status", "FAILED");

        ResponseEntity<Map> responseEntity =
                ResponseEntity.ok(inventoryResponse);

        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
                .thenReturn(responseEntity);

        Map<String, Object> result = orderService.createOrder(request);

        assertEquals("FAILED", result.get("status"));
    }


    @Test
    void shouldHandleInventoryException() {

        OrderRequest request = new OrderRequest();
        request.setProductId("1001");
        request.setQuantity(5);

        when(restTemplate.postForEntity(anyString(), any(), eq(Map.class)))
                .thenThrow(new RuntimeException("Service down"));

        Map<String, Object> result = orderService.createOrder(request);

        assertEquals("FAILED", result.get("status"));
        assertEquals("Inventory service unavailable", result.get("message"));
    }

}
