package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.State;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
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


    @Test
    public void insertIntoRDS() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(1232143.1);
        product1.setDescription("Lets");
        product1.setCreatedTime(new Date());
        product1.setState(State.ACTIVE);
        product1.setIsPrime(false);
        Category category = new Category();
        category.setId(1L);
        category.setName("cate1");
        category.setDescription("dummy");
        product1.setCategory(category);
        productRepo.save(product1);
    }

}