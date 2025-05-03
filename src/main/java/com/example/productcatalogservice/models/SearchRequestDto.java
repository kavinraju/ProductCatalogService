package com.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {
    private String query;
    private Integer pageSize;
    private Integer pageNumber;
}
