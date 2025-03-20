package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto extends BaseModel {
    private String name;
    private String description;
    private Double price;
    private CategoryDto category;
    private String imageUrl;

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
