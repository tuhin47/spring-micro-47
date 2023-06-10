package com.microservice.productservice.conttoller.Impl;

import com.microservice.productservice.conttoller.ProductController;
import com.microservice.productservice.payload.request.ProductRequest;
import com.microservice.productservice.payload.response.ProductResponse;
import com.microservice.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {

        log.info("ProductController | addProduct is called");

        log.info("ProductController | addProduct | productRequest : " + productRequest.toString());

        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {

        log.info("ProductController | getProductById is called");

        log.info("ProductController | getProductById | productId : " + productId);

        ProductResponse productResponse
                = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity
    ) {

        log.info("ProductController | reduceQuantity is called");

        log.info("ProductController | reduceQuantity | productId : " + productId);
        log.info("ProductController | reduceQuantity | quantity : " + quantity);

        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public void deleteProductById(@PathVariable("id") long productId) {
        productService.deleteProductById(productId);
    }

    @Override
    public ResponseEntity<Page<ProductResponse>> getAllProductBySearch(List<SearchCriteria> searchCriteria, HttpServletRequest request) {
        return new ResponseEntity<>(productService.getAllProductBySearch(searchCriteria,request), HttpStatus.OK);
    }
}