package com.nishi.inventory.factory;

import com.nishi.inventory.controller.entity.ProductRequest;
import com.nishi.inventory.model.ElectronicProduct;
import com.nishi.inventory.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {
    public Product createProduct(ProductRequest request) {

        if (request.type().equals("electronic")) {

            ElectronicProduct product = new ElectronicProduct();
            product.setWarranty(String.valueOf(request.warrantyInMonths()));
            return product;
        }

        throw new IllegalArgumentException("Invalid type");
    }

}
