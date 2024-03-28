package me.tuhin47.orderservice.repository;

import me.tuhin47.orderservice.model.Order;
import me.tuhin47.payload.response.TopOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("select NEW me.tuhin47.payload.response.TopOrderDto(o.createdBy, count(distinct o.id), sum(o.amount)) from Order o where o.orderDate between coalesce( :orderDateStart, date ('2000-01-01') ) and coalesce (:orderDateEnd , date ('2050-01-01') )and o.orderStatus = :orderStatus group by o.createdBy ")
    Page<TopOrderDto> getTopOrderByDateRange(Instant orderDateStart, Instant orderDateEnd, String orderStatus , Pageable pageable);

}