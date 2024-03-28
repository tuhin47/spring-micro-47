package me.tuhin47.productservice.service;

import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.payload.response.ProductsPrice;
import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.payload.response.ProductResponseExporter;
import me.tuhin47.productservice.payload.response.ProductTypeCountReport;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {

    String addProduct(ProductRequest productRequest);

    ProductResponse getProductById(String productId);

    void reduceQuantity(String productId, long quantity);

    void deleteProductById(String productId);

    List<ProductTypeCountReport> getProductTypeReport();

    Page<ProductResponseExporter> getAllProductBySearch(List<SearchCriteria> searchCriteria, HttpServletRequest request);

    ProductsPrice getProductsPrice(String[] ids);
}
