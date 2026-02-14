package com.nishi.inventory;

import com.nishi.inventory.dto.BatchDTO;
import com.nishi.inventory.model.Batch;
import com.nishi.inventory.model.BatchIdCK;
import com.nishi.inventory.model.ElectronicProduct;
import com.nishi.inventory.model.Product;
import com.nishi.inventory.repository.BatchRepository;
import com.nishi.inventory.repository.ProductRepository;
import com.nishi.inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BatchRepository batchRepository;

    private Product product;

    @BeforeEach
    void setup() {
        product = new ElectronicProduct();
        product.setProductId("1001");
        product.setProductName("Laptop");
    }

    @Test
    void shouldReturnProductDetails_WhenProductExists() {

        when(productRepository.findById("1001"))
                .thenReturn(Optional.of(product));

        when(batchRepository.findBatchesByProductId("1001"))
                .thenReturn(List.of(new BatchDTO()));

        Map response = inventoryService.getProductDetailsFromId("1001");

        assertNotNull(response);
        assertEquals("1001", response.get("productId"));
        assertEquals("Laptop", response.get("productName"));
        assertTrue(response.containsKey("batches"));
    }

    @Test
    void shouldReturnNull_WhenProductNotFound() {

        when(productRepository.findById("9999"))
                .thenReturn(Optional.empty());

        Map response = inventoryService.getProductDetailsFromId("9999");

        assertNull(response);
    }

    @Test
    void shouldReserveInventorySuccessfully() {

        Batch batch = mock(Batch.class);
        BatchIdCK batchIdCK = mock(BatchIdCK.class);

        when(batch.getBatchIdCK()).thenReturn(batchIdCK);
        when(batch.getQuantity()).thenReturn(new BigDecimal(2));
        when(batch.getReservedQuantity()).thenReturn(new BigDecimal(0));
        when(batch.getBatchIdCK().getBatchId()).thenReturn("B1");

        when(productRepository.findById("1001"))
                .thenReturn(Optional.of(product));

        when(batchRepository.findAllByProduct_ProductIdOrderByExpiryDateDesc("1001"))
                .thenReturn(List.of(batch));

        Map response = inventoryService.reserveInventory("1001", 2);

        assertNotNull(response);
        assertEquals("1001", response.get("productId"));
        assertEquals(2, response.get("quantity"));

        List<String> reservedIds =
                (List<String>) response.get("reservedFromBatchIds");

        assertFalse(reservedIds.isEmpty());

        verify(batchRepository, times(1)).saveAll(any());
    }

    @Test
    void shouldFailReservation_WhenInsufficientStock() {

        Batch batch = mock(Batch.class);

        when(batch.getQuantity()).thenReturn(new BigDecimal(10));
        when(batch.getReservedQuantity()).thenReturn(new BigDecimal(10));

        when(productRepository.findById("1001"))
                .thenReturn(Optional.of(product));

        when(batchRepository.findAllByProduct_ProductIdOrderByExpiryDateDesc("1001"))
                .thenReturn(List.of(batch));

        Map response = inventoryService.reserveInventory("1001", 20);

        List<String> reservedIds =
                (List<String>) response.get("reservedFromBatchIds");

        assertTrue(reservedIds.isEmpty());

        verify(batchRepository, never()).saveAll(any());
    }
}
