package com.nishi.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nishi.inventory.controller.entity.InventoryUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnProductDetails() throws Exception {

        mockMvc.perform(get("/inventory/1001"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateInventory() throws Exception {

        InventoryUpdateRequest request = new InventoryUpdateRequest();
        request.setProductId("1001");
        request.setQuantity(10);

        mockMvc.perform(post("/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
