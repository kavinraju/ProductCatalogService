package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.State;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;

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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
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

    @Test
    public void TestGetAllProducts_RunSuccessFully() {
        // Arrange
        when(iProductService.getAllProducts()).thenReturn(new ArrayList<>());

        // Act
        List<ProductDto> productDtos = productController.getAllProducts();

        // Assert
        assertNotNull(productDtos);
    }

    @Test
    public void TestGetAllProducts_ReturnNull() {
        // Arrange
        when(iProductService.getAllProducts()).thenReturn(null);

        // Act
        List<ProductDto> productDtos = productController.getAllProducts();

        // Assert
        assertNull(productDtos);
    }

    @Test
    public void TestCreateProduct_Successfully() {
        // Arrange
        Product product = new Product();
        product.setName("Phone");
        product.setPrice(10000.0);
        product.setDescription("This is a phone");
        product.setState(State.ACTIVE);
        product.setId(1L);

        when(iProductService.createProduct(any(Product.class))).thenReturn(product);

        // Act
        ProductDto createdProduct = productController.createProduct(new ProductDto());

        // Assert
        assertNotNull(createdProduct);
        assertEquals(createdProduct.getId(), 1L);
        assertEquals(createdProduct.getName(), "Phone");
    }


    @Test
    public void TestReplaceProduct_Successfully() {
        // Arrange
        Long id = 1L;
        Product product = new Product();
        product.setName("Phone");
        product.setPrice(10000.0);
        product.setDescription("This is a phone");
        product.setState(State.ACTIVE);
        product.setId(1L);

        when(iProductService.replaceProduct(eq(id), any(Product.class))).thenReturn(product);

        // Act
        ProductDto createdProduct = productController.replaceProduct(id, new ProductDto());

        // Assert
        assertNotNull(createdProduct);
        assertEquals(createdProduct.getId(), 1L);
        assertEquals(createdProduct.getName(), "Phone");
    }

    @Test
    public void TestReplaceProduct_WithInvalidId() {
        // Arrange
        Long id = -1L;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productController.replaceProduct(id, new ProductDto()));

        // Assert
        assertEquals("Invalid ID " + id, exception.getMessage());
        verify(iProductService, times(0)).replaceProduct(id, new Product());
    }

    @Test
    public void TestDeleteProduct_Success() {
        // Arrange
        Long id = 1L;
        when(iProductService.deleteProduct(id)).thenReturn(true);

        // Act
        boolean isDeleted = productController.deleteProduct(id);

        // Assert
        assertTrue(isDeleted);
    }

    @Test
    public void TestDeleteProduct_Failed() {
        // Arrange
        Long id = 1L;
        when(iProductService.deleteProduct(id)).thenReturn(false);

        // Act
        boolean isDeleted = productController.deleteProduct(id);

        // Assert
        assertFalse(isDeleted);
    }
}

/**
 * HomeWork(17/03/2025): Write unit tests for other methods in ProductController
 */