package com.microservice.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservice.productservice.entity.Product;
import com.microservice.productservice.payload.request.ProductRequest;
import com.microservice.productservice.payload.response.ProductResponse;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {

    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);

    void deleteProductById(long productId);

    Page<Product> getAllProductBySearch(List<SearchCriteria> searchCriteria, HttpServletRequest request);
}
