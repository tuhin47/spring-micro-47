package me.tuhin47.orderservice.repository;

import me.tuhin47.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,String> {
}
