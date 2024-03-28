package me.tuhin47.productservice.repository;

import me.tuhin47.productservice.domain.entity.Product;
import me.tuhin47.productservice.payload.response.ProductTypeCountReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

    @Query(name = "Product.countByProductType")
    List<ProductTypeCountReport> countByProductType();

    @Query("select p from Product p where p.price between coalesce(:priceStart, 0) and coalesce(:priceEnd, 2147483647)")
    List<Product> findByPriceBetween(@Param("priceStart") @Nullable Double priceStart, @Param("priceEnd") @Nullable Double priceEnd);

}
