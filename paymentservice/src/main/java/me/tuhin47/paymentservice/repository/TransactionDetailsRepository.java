package me.tuhin47.paymentservice.repository;

import me.tuhin47.paymentservice.model.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, String> {

    Optional<TransactionDetails> findByOrderId(String orderId);
}
