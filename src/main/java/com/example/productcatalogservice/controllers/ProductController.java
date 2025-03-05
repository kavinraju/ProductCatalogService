package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    IProductService iProductService;

    @GetMapping("/products/{id}")
    public ProductDto getProductDetails(@PathVariable Long id) {
        if(id <=0 ){
            throw new IllegalArgumentException("Invalid ID " + id);
        }
        Product product = iProductService.getProductById(id);
        if (product == null) {
            return null;
        }

        return from(product);
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        List<Product> products = iProductService.getAllProducts();
        if (products == null) {
            return null;
        }
        return products.stream().map(val -> from(val)).toList();
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return null;
    }

    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        if(id <=0 ){
            throw new IllegalArgumentException("Invalid ID " + id);
        }
        Product product = iProductService.replaceProduct(id, from(productDto));
        if (product == null) {
            return null;
        }
        return from(product);
    }

    @DeleteMapping("/products")
    public boolean deleteProduct() {
        return false;
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        if(productDto.getCategory() != null){
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        product.setDescription(productDto.getDescription());
        product.setCreatedTime(productDto.getCreatedTime());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        return product;
    }
}
