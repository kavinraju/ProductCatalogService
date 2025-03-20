package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class)
class ProductControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetProducts_RunSuccessfully() throws Exception {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("iPhone");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Macbook");

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        // Act
        when(productService.getAllProducts()).thenReturn(productList);

        ProductDto productDto1 = new ProductDto();
        productDto1.setId(1L);
        productDto1.setName("iPhone");

        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2L);
        productDto2.setName("Macbook");

        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(productDto1);
        productDtoList.add(productDto2);

        // assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productList)));
    }

    @Test
    public void Test_CreateProduct_RunSuccessfully() throws Exception {
        // Arrange
        ProductDto productDto = new ProductDto();
        productDto.setId(10L);
        productDto.setPrice(100000D);
        productDto.setName("SuperComputer");

        Product product = new Product();
        product.setId(10L);
        product.setPrice(100000D);
        product.setName("SuperComputer");

        when(productService.createProduct(any(Product.class)))
                .thenReturn(product);

        // Act and Assert
        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }
}