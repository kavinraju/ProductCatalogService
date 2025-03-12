package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    // FetchType.LAZY - fetch only if required
    // Here Products will not be fetched
    @Test
    @Transactional
    public void testFetchLazyType1() {
        Category category = categoryRepo.findById(2L).get();
        System.out.println(category.getName());
//        for (Product product : category.getProductList()) {
//            System.out.println(product.getName());
//        }
    }

    // FetchType.LAZY - fetch only if required
    // Here Products will be fetched
    @Test
    @Transactional
    public void testFetchLazyType2() {
        Category category = categoryRepo.findById(2L).get();
        System.out.println(category.getName());
        for (Product product : category.getProductList()) {
            System.out.println(product.getName());
        }
    }

    // FetchType.EAGER - fetch only if required
    // Here Products will be fetched
    @Test
    @Transactional
    public void testFetchEagerType1() {
        Category category = categoryRepo.findById(2L).get();
        System.out.println(category.getName());
        for (Product product : category.getProductList()) {
            System.out.println(product.getName());
        }
    }

    // FetchType.EAGER - fetch only if required
    // Here Products will be fetched
    @Test
    @Transactional
    public void testFetchEagerType2() {
        Category category = categoryRepo.findById(2L).get();
        System.out.println(category.getName());
//        for (Product product : category.getProductList()) {
//            System.out.println(product.getName());
//        }
    }

    // Fetch Model will help you to decide how to get the data
}