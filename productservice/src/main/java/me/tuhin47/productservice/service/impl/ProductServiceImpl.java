package me.tuhin47.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.productservice.entity.Product;
import me.tuhin47.productservice.exception.ProductServiceExceptions;
import me.tuhin47.productservice.payload.mapper.ProductMapper;
import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.payload.response.ProductResponseExporter;
import me.tuhin47.productservice.repository.ProductRepository;
import me.tuhin47.productservice.service.ProductService;
import me.tuhin47.searchspec.GenericSpecification;
import me.tuhin47.searchspec.RecordNavigationManager;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public String addProduct(ProductRequest productRequest) {
        Product entity = productMapper.toEntity(productRequest);
        return productRepository.save(entity).getId();
    }

    @Override
    public ProductResponse getProductById(String productId) {

        return productMapper.toDto(productRepository.findById(productId)
                                                    .orElseThrow(() -> ProductServiceExceptions.PRODUCT_NOT_FOUND.apply(productId)));
    }

    @Override
    public void reduceQuantity(String productId, long quantity) {

        log.info("Reduce Quantity {} for Id: {}", quantity, productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> ProductServiceExceptions.PRODUCT_NOT_FOUND.apply(productId));

        if (product.getQuantity() < quantity) {
            throw ProductServiceExceptions.INSUFFICIENT_QUANTITY.get();
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated Successfully");
    }

    @Override
    public void deleteProductById(String productId) {
        if (!productRepository.existsById(productId)) {
            log.info("Im in this loop {}", !productRepository.existsById(productId));
            throw ProductServiceExceptions.PRODUCT_NOT_FOUND.apply(productId);
        }
        log.info("Deleting Product with id: {}", productId);
        productRepository.deleteById(productId);

    }

    @Override
    public Page<ProductResponseExporter> getAllProductBySearch(List<SearchCriteria> searchCriteria, HttpServletRequest request) {
        var productSpecification = new GenericSpecification<Product>(searchCriteria);
        return productRepository.findAll(productSpecification, RecordNavigationManager.getPageable(request)).map(productMapper::toExporterDto);
    }

}
