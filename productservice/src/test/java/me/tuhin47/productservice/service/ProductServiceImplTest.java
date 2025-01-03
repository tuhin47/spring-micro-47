package me.tuhin47.productservice.service;

import me.tuhin47.config.exception.apierror.CustomException;
import me.tuhin47.config.exception.apierror.EntityNotFoundException;
import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.productservice.domain.entity.Product;
import me.tuhin47.productservice.domain.enums.ProductType;
import me.tuhin47.productservice.payload.mapper.ProductMapper;
import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.repository.ProductRepository;
import me.tuhin47.productservice.rules.DiscountRuleEngine;
import me.tuhin47.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductService productService;
    private DiscountRuleEngine discountRuleEngine;

    @BeforeEach
    void setup() {
        ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
        productRepository = mock(ProductRepository.class);
        discountRuleEngine = mock(DiscountRuleEngine.class);
        productService = new ProductServiceImpl(mapper, productRepository, discountRuleEngine);
    }

    @Test
    void test_When_addProduct_isSuccess() {

        ProductRequest productRequest = getMockProductRequest();
        Product product = getMockProductDetails();

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        String productId = productService.addProduct(productRequest);

        verify(productRepository, times(1))
            .save(any());

        assertEquals(product.getId(), productId);
    }

    @Test
    void test_When_GetProductById_isSuccess() {
        Product product = getMockProductDetails();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        ProductResponse productResponse = productService.getProductById("1");
        //Verification
        verify(productRepository, times(1)).findById(anyString());

        //Assert
        assertNotNull(productResponse);
        assertEquals(product.getId(), productResponse.getId());

    }

    @Test
    void test_When_GetProductById_isNotFound() {

        when(productRepository.findById(anyString())).thenReturn(Optional.empty());
        //Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.getProductById("1"));
        assertEquals("Product was not found for parameters {id=1}", exception.getMessage());

        //Verify
        verify(productRepository, times(1)).findById(anyString());

    }


    @Test
    void test_When_deleteProductById_isSuccess() {
        Product product = getMockProductDetails();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        productService.deleteProductById(product.getId());
    }

    @Test
    void test_When_reduceQuantity_isSuccess() {
        Product product = getMockProductDetails();
        String productId = "1";
        long quantity = 5;
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        productService.reduceQuantity(productId, quantity);
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void test_When_reduceQuantity_isFailed_when_productId_isNotFound() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        //Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.reduceQuantity("1", 1));
        assertEquals("Product was not found for parameters {id=1}", exception.getMessage());

        //Verify
        verify(productRepository, times(0)).save(any());
    }

    @Test
    void test_When_reduceQuantity_isFailed_when_insufficientQuantity() {
        Product product = getMockProductDetails();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        CustomException exception = assertThrows(CustomException.class, () -> productService.reduceQuantity("1", 11));
        assertEquals("INSUFFICIENT_QUANTITY", exception.getErrorCode());
        assertEquals("Product does not have sufficient Quantity", exception.getMessage());

        //Verify
        verify(productRepository, times(0)).save(any());
    }

    private Product getMockProductDetails() {

        return Product.builder()
                      .productName("iphone")
                      .quantity(10)
                      .id("1")
                      .price(1000)
                      .build();

    }

    private ProductRequest getMockProductRequest() {
        return new ProductRequest("iphone", ProductType.ELECTRONIC, 1000, 10);
    }
}
