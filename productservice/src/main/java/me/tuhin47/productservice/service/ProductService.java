package me.tuhin47.productservice.service;

import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.payload.response.ProductResponse;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {

    String addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);

    void deleteProductById(long productId);

    Page<ProductResponse> getAllProductBySearch(List<SearchCriteria> searchCriteria, HttpServletRequest request);
}
