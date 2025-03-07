package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class ProductRepoTest {

    @Autowired
    ProductRepo productRepo;

    @Test
    @Transactional
    public void tesFindProductByOrderByPriceDesc() {
        List<Product> productList = productRepo.findProductByOrderByPriceDesc();
        for (Product product : productList) {
            System.out.println(product.getName() + ": " + product.getPrice());
        }
    }

    @Test
    @Transactional
    public void tesFindProductNameById() {
        System.out.println(productRepo.findProductNameById(1L));
    }

    @Test
    @Transactional
    public void testFindCategoryNameFromProductId() {
        System.out.println(productRepo.findCategoryNameFromProductId(1L));
    }

    @Test
    @Transactional
    public void testFindProductByPriceBetween() {
        List<Product> productList = productRepo.findProductByPriceBetween(100.0, 400.0);
        for (Product product : productList) {
            System.out.println(product.getName() + ": " + product.getPrice());
        }
    }

}