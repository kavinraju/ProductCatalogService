package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface ISearchService {
    List<Product> searchProducts(String query, Integer pageNumber, Integer pageSize);
}
