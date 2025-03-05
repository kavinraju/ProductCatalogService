package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.BaseModel;
import com.example.productcatalogservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDto extends BaseModel {
    private String name;
    private String description;
    private List<Product> productList;

    @Override
    public String toString() {
        return "CategoryDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", productList=" + productList +
                '}';
    }
}
