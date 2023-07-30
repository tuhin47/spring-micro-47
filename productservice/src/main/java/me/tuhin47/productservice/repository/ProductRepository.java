package me.tuhin47.productservice.repository;

import me.tuhin47.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, String>, JpaSpecificationExecutor<Product> {

}
