package me.tuhin47.paymentservice.service;

import me.tuhin47.paymentservice.exception.PaymentServiceCustomException;
import me.tuhin47.paymentservice.model.TransactionDetails;
import me.tuhin47.paymentservice.payload.PaymentRequest;
import me.tuhin47.paymentservice.payload.PaymentResponse;
import me.tuhin47.paymentservice.repository.TransactionDetailsRepository;
import me.tuhin47.paymentservice.utils.PaymentMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
public class PaymentServiceImplTest {

    private TransactionDetailsRepository transactionDetailsRepository;

    PaymentService paymentService;

    @BeforeEach
    void setup(){
        transactionDetailsRepository = mock(TransactionDetailsRepository.class);
        paymentService = new PaymentServiceImpl(transactionDetailsRepository);
    }

    @Test
    void test_When_doPayment_isSuccess() {

        PaymentRequest paymentRequest = getMockPaymentRequest();

        TransactionDetails transactionDetails = getMockTransactionDetails();
        when(transactionDetailsRepository.save(any(TransactionDetails.class))).thenReturn(transactionDetails);

        long transactionId = paymentService.doPayment(paymentRequest);
        verify(transactionDetailsRepository, times(1))
                .save(any());

        assertEquals(transactionDetails.getId(), transactionId);
    }

    @Test
    void test_When_getPaymentDetailsByOrderId_isSuccess() {

        TransactionDetails transactionDetails = getMockTransactionDetails();

        when(transactionDetailsRepository.findByOrderId(anyLong())).thenReturn(Optional.of(transactionDetails));

        //Actual
        PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderId(1);

        //Verification
        verify(transactionDetailsRepository, times(1)).findByOrderId(anyLong());

        //Assert
        assertNotNull(paymentResponse);
        assertEquals(transactionDetails.getId(), paymentResponse.getPaymentId());
    }

    @Test
    void test_When_getPaymentDetailsByOrderId_isNotFound() {

        when(transactionDetailsRepository.findByOrderId(anyLong())).thenReturn(Optional.ofNullable(null));

        //Assert
        PaymentServiceCustomException exception
                = assertThrows(PaymentServiceCustomException.class, () -> paymentService.getPaymentDetailsByOrderId(1));
        assertEquals("TRANSACTION_NOT_FOUND", exception.getErrorCode());
//        assertEquals("TransactionDetails with given id not found", exception.getMessage());

        //Verify
        verify(transactionDetailsRepository, times(1)).findByOrderId(anyLong());
    }

    private PaymentRequest getMockPaymentRequest() {
        return PaymentRequest.builder()
                .amount(500)
                .orderId(1)
                .paymentMode(PaymentMode.CASH)
                .referenceNumber(null)
                .build();

    }

    private TransactionDetails getMockTransactionDetails() {
        return TransactionDetails.builder()
                .id(1L)
                .orderId(1)
                .paymentDate(Instant.now())
                .paymentMode(PaymentMode.CASH.name())
                .paymentStatus("SUCCESS")
                .referenceNumber(null)
                .amount(500)
                .build();
    }
}