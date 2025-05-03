package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JpaSearchService implements ISearchService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> searchProducts(String query, Integer pageNumber, Integer pageSize) {
        List<Product>  productList = productRepo.findByNameEquals(query, PageRequest.of( pageNumber, pageSize));
        return productList;
    }
}
