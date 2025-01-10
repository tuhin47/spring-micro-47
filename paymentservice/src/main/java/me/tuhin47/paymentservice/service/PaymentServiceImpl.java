package me.tuhin47.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.payload.request.TransactionRequest;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.paymentservice.model.TransactionDetails;
import me.tuhin47.paymentservice.payload.TransactionDetailsMapper;
import me.tuhin47.paymentservice.repository.TransactionDetailsRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TransactionDetailsMapper transactionDetailsMapper;
    private final TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public String doPayment(TransactionRequest transactionRequest) {

        TransactionDetails details = transactionDetailsMapper.toEntity(transactionRequest);

        var transactionDetails = transactionDetailsRepository.save(details);

        log.info("Transaction Completed with Id: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {


        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId)
                                                                            .orElse(null);


        return transactionDetailsMapper.toDto(transactionDetails);
    }
}
