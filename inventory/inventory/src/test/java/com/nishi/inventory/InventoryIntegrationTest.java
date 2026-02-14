package com.nishi.inventory;

import com.nishi.inventory.model.ElectronicProduct;
import com.nishi.inventory.model.Product;
import com.nishi.inventory.repository.ProductRepository;
import com.nishi.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class InventoryIntegrationTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
        assertNotNull(inventoryService);
    }

    @Test
    void shouldFetchProductFromH2() {

        Product product = new ElectronicProduct();
        product.setProductId("1003");
        product.setProductName("Tablet");

        productRepository.save(product);

        Map response = inventoryService.getProductDetailsFromId("1003");

        assertNotNull(response);
        assertEquals("Tablet", response.get("productName"));
    }
}
