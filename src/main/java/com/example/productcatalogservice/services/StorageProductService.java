package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("sps")
@Primary
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Product getProductById(Long id) {
        // Find in redis
        Product product = (Product) redisTemplate.opsForHash().get("products", id);
        if (product != null) {
            return product;
        }

        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            redisTemplate.opsForHash().put("products", id, optionalProduct.get());
        }
        return optionalProduct.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return productRepo.save(product);
    }

    @Override
    public Boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) {
            return false;
        }
        productRepo.deleteById(id);
        return true;
    }

    @Override
    public Product createProduct(Product product) {
        Optional<Product> optionalProduct = productRepo.findById(product.getId());
        System.out.println(optionalProduct.isEmpty());
        if (optionalProduct.isEmpty()) {
            return productRepo.save(product);
        }
        return optionalProduct.get();
    }
}
