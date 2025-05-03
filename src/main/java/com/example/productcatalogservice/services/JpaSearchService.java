package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.SortParam;
import com.example.productcatalogservice.dtos.SortType;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JpaSearchService implements ISearchService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Page<Product> searchProducts(String query, Integer pageNumber, Integer pageSize, List<SortParam> sortParams) {
//        Sort sort = Sort.by("price").descending().and(Sort.by("id"));
        Sort sort = null;
        if (!sortParams.isEmpty()) {
            SortParam sortParam = sortParams.get(0);
            if (sortParam.getSortType().equals(SortType.ASC)) {
                sort = Sort.by(sortParam.getParamName());
            } else {
                sort = Sort.by(sortParam.getParamName()).descending();
            }

            for (int i = 1; i < sortParams.size(); i++) {
                if (sortParam.getSortType().equals(SortType.ASC)) {
                    sort = sort.and(Sort.by(sortParam.getParamName()));
                } else {
                    sort = sort.and(Sort.by(sortParam.getParamName()).descending());
                }
            }

        }
        return productRepo.findByNameEquals(query, PageRequest.of(pageNumber, pageSize, sort));
    }
}
