package me.tuhin47.productservice.payload.mapper;

import me.tuhin47.payload.response.ProductResponse;
import me.tuhin47.productservice.domain.entity.Product;
import me.tuhin47.productservice.payload.request.ProductRequest;
import me.tuhin47.productservice.payload.response.ProductResponseExporter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest productRequest);

    ProductResponse toDto(Product product);

    @Mapping(source = "id", target = "productId")
    ProductResponseExporter toExporterDto(Product product);
}