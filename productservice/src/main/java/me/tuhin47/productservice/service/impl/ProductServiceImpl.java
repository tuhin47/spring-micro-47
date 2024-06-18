package me.tuhin47.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.exception.EntityNotFoundException;
import me.tuhin47.exception.common.ProductServiceExceptions;
import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.payload.response.ProductsPrice;
import me.tuhin47.productservice.domain.entity.Product;
import me.tuhin47.productservice.payload.mapper.ProductMapper;
import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.payload.response.ProductResponseExporter;
import me.tuhin47.productservice.payload.response.ProductTypeCountReport;
import me.tuhin47.productservice.repository.ProductRepository;
import me.tuhin47.productservice.rules.DiscountRuleEngine;
import me.tuhin47.productservice.service.ProductService;
import me.tuhin47.searchspec.GenericSpecification;
import me.tuhin47.searchspec.RecordNavigationManager;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final DiscountRuleEngine discountRuleEngine;

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

        log.info("Deleting Product with id: {}", productId);
        try {
            productRepository.deleteById(productId);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException(Product.class, HttpStatus.UNPROCESSABLE_ENTITY, "id", productId);
        }

    }

    @Override
    public List<ProductTypeCountReport> getProductTypeReport() {

        return productRepository.countByProductType();
    }

    @Override
    public Page<ProductResponseExporter> getAllProductBySearch(List<SearchCriteria> searchCriteria, HttpServletRequest request) {
        var productSpecification = new GenericSpecification<Product>(searchCriteria);
        return productRepository.findAll(productSpecification, RecordNavigationManager.getPageable(request)).map(productMapper::toExporterDto);
    }

    @Override
    public ProductsPrice getProductsPrice(String[] ids) {
        if (ids == null || ids.length <= 0) {
            return null;
        }
        List<ProductResponse> productResponses = productRepository.findAllById(Arrays.asList(ids))
                                                                  .stream().map(productMapper::toDto).toList();
        ProductsPrice productsPrice = new ProductsPrice(productResponses, productResponses.stream().mapToDouble(ProductResponse::getPrice).sum());
        discountRuleEngine.applyRules(productsPrice);

        return productsPrice;
    }
}
