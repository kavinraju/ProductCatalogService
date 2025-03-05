package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);
        if (fakeStoreProductDto != null && fakeStoreProductDto.getStatusCode().equals(200)) {
            return from(fakeStoreProductDto.getBody());
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<List<FakeStoreProductDto>> fakeStoreProductDtos = restTemplate.exchange("'https://fakestoreapi.com/products",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                });
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtos = restTemplate.getForEntity("https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);

        System.out.println(fakeStoreProductDtos.getStatusCode());
        if (fakeStoreProductDtos != null && fakeStoreProductDtos.getStatusCode().equals(200)) {
            FakeStoreProductDto[] result = fakeStoreProductDtos.getBody();
            return Arrays.stream(result).map(this::from).collect(Collectors.toList());
        }

        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {

        FakeStoreProductDto input = from(product);
        System.out.println("replaceProduct:: input" + input);
        FakeStoreProductDto fakeStoreProductDto = requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.PUT, input, FakeStoreProductDto.class, id).getBody();
        System.out.println("replaceProduct:: fakeStoreProductDto" + fakeStoreProductDto);
        if (fakeStoreProductDto == null) {
            return null;
        }
        return from(fakeStoreProductDto);
    }

    @Override
    public Boolean deleteProduct(Long id) {

        return false;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }


    public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if (product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
