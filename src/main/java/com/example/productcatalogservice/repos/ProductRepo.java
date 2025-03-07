package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product save(Product product);
//    Optional<Product> fetchById(Long id);

    void deleteById(Long aLong);

    List<Product> findProductByOrderByPriceDesc();

    @Query("SELECT p.name from Product p where p.id=?1")
    String findProductNameById(Long id); // positional association

    @Query("SELECT c.name from Category c join Product p on p.category.id = c.id where p.id=:pid")
    String findCategoryNameFromProductId(Long pid); // value association

    List<Product> findProductByPriceBetween(Double from, Double to);
}
