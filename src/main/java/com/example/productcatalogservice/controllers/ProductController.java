package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    @Qualifier("fkps")
    IProductService iProductService;

    @GetMapping("/{id}")
    public ProductDto getProductDetails(@PathVariable Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID " + id);
        }
        Product product = iProductService.getProductById(id);
        if (product == null) {
            return null;
        }

        return from(product);
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product> products = iProductService.getAllProducts();
        if (products == null) {
            return null;
        }
        return products.stream().map(val -> from(val)).toList();
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = from(productDto);
        Product response = iProductService.createProduct(product);
        return from(response);
    }

    @PutMapping("/{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID " + id);
        }
        Product product = iProductService.replaceProduct(id, from(productDto));
        if (product == null) {
            return null;
        }
        return from(product);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return iProductService.deleteProduct(id);
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
            categoryDto.setId(product.getCategory().getId());
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
        product.setId(productDto.getId());
        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
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
