package com.microservice.productservice.conttoller;

import com.microservice.productservice.payload.request.ProductRequest;
import com.microservice.productservice.payload.response.ProductResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Api(tags = "Product API")
@RequestMapping("/product")
public interface ProductController {
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    @ApiOperation(value = "Add a new product")
    ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest);

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by ID")
    ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId);

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/reduceQuantity/{id}")
    @ApiOperation(value = "Reduce the quantity of a product")
    ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity);

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by ID")
    void deleteProductById(@PathVariable("id") long productId);

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    @ApiOperation(value = "Get All product By Search, Pagination supported")
    ResponseEntity<Page<ProductResponse>> getAllProductBySearch(@RequestBody List<SearchCriteria> searchCriteria, @ApiIgnore HttpServletRequest request);

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/all/excel")
    @ApiOperation("Export Items")
    ResponseEntity<byte[]> exportExcel(@RequestBody List<SearchCriteria> searchCriteria, @ApiIgnore HttpServletRequest request) throws IOException;
}
