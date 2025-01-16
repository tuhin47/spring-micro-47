package me.tuhin47.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import me.tuhin47.exporter.ExporterType;
import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.payload.response.ProductsPrice;
import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.payload.response.ProductResponseExporter;
import me.tuhin47.productservice.payload.response.ProductTypeCountReport;
import me.tuhin47.searchspec.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Tag(name = "Product API", description = "Endpoints for product management")
public interface ProductController {

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @Operation(summary = "Add a new product")
    ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @Operation(summary = "Get a product by ID")
    ResponseEntity<ProductResponse> getProductById(@PathVariable("id") String productId);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @Operation(summary = "Reduce the quantity of a product")
    ResponseEntity<Void> reduceQuantity(@PathVariable("id") String productId, @RequestParam long quantity);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_ADMIN)")
    @Operation(summary = "Delete a product by ID")
    ResponseEntity<Void> deleteProductById(@PathVariable("id") String productId);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_ADMIN)")
    @Operation(summary = "Get all products by search with pagination")
    ResponseEntity<Page<ProductResponseExporter>> getAllProductBySearch(
        @RequestBody(required = false) List<SearchCriteria> searchCriteria,
        HttpServletRequest request);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_ADMIN)")
    @Operation(summary = "Get products count by product type")
    ResponseEntity<List<ProductTypeCountReport>> getProductTypeCount();

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_ADMIN)")
    @Operation(summary = "Export items")
    ResponseEntity<byte[]> exportExcel(
        @RequestBody(required = false) List<SearchCriteria> searchCriteria,
        @RequestParam(value = "type", defaultValue = "EXCEL") ExporterType exporterType,
        HttpServletRequest request) throws IOException;

    @Operation(summary = "Get prices for products")
    ResponseEntity<ProductsPrice> getProductPrices(String[] ids);
}
