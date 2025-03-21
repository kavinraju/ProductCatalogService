package com.example.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseModel {
    private String name;
    private String description;
    private Double price;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    private Boolean isPrime;
    private String imageUrl;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", isPrime=" + isPrime +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
