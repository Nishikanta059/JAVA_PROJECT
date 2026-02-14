package com.nishi.order;

import com.nishi.order.controller.OrderController;
import com.nishi.order.controller.entity.OrderRequest;
import com.nishi.order.factory.ResponseFactory;
import com.nishi.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @Mock
    ResponseFactory responseFactory;

    @Test
    void shouldCallServiceAndReturnResponse() {

        // Arrange
        OrderRequest request = new OrderRequest();
        request.setProductId("1001");
        request.setQuantity(5);

        Map<String, Object> serviceResponse = new HashMap<>();
        serviceResponse.put("status", "SUCCESS");

        ResponseEntity<Map<String, Object>> responseEntity =
                ResponseEntity.ok(serviceResponse);

        when(orderService.createOrder(any(OrderRequest.class)))
                .thenReturn(serviceResponse);

        when(responseFactory.buildResponse(eq("order"), any(Map.class)))
                .thenReturn(responseEntity);

        // Act
        ResponseEntity<?> result =
                orderController.updateProductInventoryDetails(request);

        // Assert
        assertEquals(200, result.getStatusCodeValue());

        verify(orderService).createOrder(any(OrderRequest.class));
        verify(responseFactory).buildResponse(eq("order"), any(Map.class));
    }
}
