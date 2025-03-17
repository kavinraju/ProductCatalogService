package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    //@MockBean
    @MockitoBean
    private IProductService iProductService;

    @Autowired
    private ProductController productController;

    @Captor
    private ArgumentCaptor<Long> argumentCaptor;

    @Test
    public void TestGetProductDetailsById_WithValidProductId_RunSuccessfully() {
        // Arrange
        Long id = 3L;
        when(iProductService.getProductById(id)).thenReturn(new Product());

        // Act
        ProductDto productDto = productController.getProductDetails(id);

        // Assert
        assertNotNull(productDto);
    }

    @Test
    public void TestGetProductDetailsById_WithNegativeProductId_ResultsInIllegalArgumentException() {
        // Arrange
        Long id = -1L;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()->{
            productController.getProductDetails(id);
        });

        // Assert
        String expectedMessage = "Invalid ID " + id;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(iProductService, times(0)).getProductById(id);
    }

    @Test
    public void TestGetProductDetailsById_ProductServiceCalledWithCorrectArguments_RunSuccessfully() {
        // Arrange
        Long productId = 5L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Macbook");

        when(iProductService.getProductById(productId)).thenReturn(product);

        // Act
        productController.getProductDetails(productId);

        // Assert
        verify(iProductService).getProductById(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), productId);
    }
}

/**
 * HomeWork(17/03/2025): Write unit tests for other methods in ProductController
 */