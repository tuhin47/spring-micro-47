package me.tuhin47.paymentservice.service;

import me.tuhin47.paymentservice.model.TransactionDetails;
import me.tuhin47.paymentservice.payload.PaymentRequest;
import me.tuhin47.paymentservice.payload.TransactionDetailsMapper;
import me.tuhin47.paymentservice.repository.TransactionDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
public class PaymentServiceImplTest {

    private TransactionDetailsRepository transactionDetailsRepository;

    PaymentService paymentService;

    @BeforeEach
    void setup(){
        transactionDetailsRepository = mock(TransactionDetailsRepository.class);
        TransactionDetailsMapper mapper = mock(TransactionDetailsMapper.class);
        paymentService = new PaymentServiceImpl(mapper, transactionDetailsRepository);
    }

    @Test
    void test_When_doPayment_isSuccess() {

        PaymentRequest paymentRequest = getMockPaymentRequest();

        TransactionDetails transactionDetails = getMockTransactionDetails();
        when(transactionDetailsRepository.save(any(TransactionDetails.class))).thenReturn(transactionDetails);

        String transactionId = paymentService.doPayment(paymentRequest);
        verify(transactionDetailsRepository, times(1))
                .save(any());

        assertEquals(transactionDetails.getId(), transactionId);
    }

    @Test
    void test_When_getPaymentDetailsByOrderId_isSuccess() {

        TransactionDetails transactionDetails = getMockTransactionDetails();

//        when(transactionDetailsRepository.findByOrderId(anyLong())).thenReturn(Optional.of(transactionDetails));

        //Actual
//        PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderId(1);

        //Verification
//        verify(transactionDetailsRepository, times(1)).findByOrderId(anyLong());

        //Assert
//        assertNotNull(paymentResponse);
//        assertEquals(transactionDetails.getId(), paymentResponse.getPaymentId());
    }

    @Test
    void test_When_getPaymentDetailsByOrderId_isNotFound() {

//        when(transactionDetailsRepository.findByOrderId(anyLong())).thenReturn(Optional.ofNullable(null));

        //Assert
//        PaymentServiceCustomException exception = assertThrows(PaymentServiceCustomException.class, () -> paymentService.getPaymentDetailsByOrderId(1));
//        assertEquals("TRANSACTION_NOT_FOUND", exception.getErrorCode());
//        assertEquals("TransactionDetails with given id not found", exception.getMessage());

        //Verify
//        verify(transactionDetailsRepository, times(1)).findByOrderId(anyLong());
    }

    private PaymentRequest getMockPaymentRequest() {
        return PaymentRequest.builder()
                .amount(500)
//                .orderId(1) // TODO Later
//                .paymentMode(PaymentMode.CASH)
                .referenceNumber(null)
                .build();

    }

    private TransactionDetails getMockTransactionDetails() {
        return TransactionDetails.builder()
//                .id(1L) // TODO later
//                .orderId(1) // TODO later
                .paymentDate(Instant.now())
//                .paymentMode(PaymentMode.CASH.name())
                .paymentStatus("SUCCESS")
                .referenceNumber(null)
                .amount(500)
                .build();
    }
}