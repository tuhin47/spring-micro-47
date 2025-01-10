package me.tuhin47.paymentservice.service;

import me.tuhin47.core.enums.PaymentMode;
import me.tuhin47.payload.request.TransactionRequest;
import me.tuhin47.payload.response.PaymentResponse;
import me.tuhin47.paymentservice.model.TransactionDetails;
import me.tuhin47.paymentservice.payload.TransactionDetailsMapper;
import me.tuhin47.paymentservice.repository.TransactionDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PaymentServiceImplTest {

    private PaymentService paymentService;
    private TransactionDetailsRepository transactionDetailsRepository;

    @BeforeEach
    void setup() {
        transactionDetailsRepository = mock(TransactionDetailsRepository.class);
        TransactionDetailsMapper mapper = Mappers.getMapper(TransactionDetailsMapper.class);
        paymentService = new PaymentServiceImpl(mapper, transactionDetailsRepository);
    }

    @Test
    void test_When_doPayment_isSuccess() {

        TransactionRequest transactionRequest = getMockPaymentRequest();

        TransactionDetails transactionDetails = getMockTransactionDetails();
        when(transactionDetailsRepository.save(any(TransactionDetails.class))).thenReturn(transactionDetails);

        String transactionId = paymentService.doPayment(transactionRequest);
        verify(transactionDetailsRepository, times(1))
            .save(any());

        assertEquals(transactionDetails.getId(), transactionId);
    }

    @Test
    void test_When_getPaymentDetailsByOrderId_isSuccess() {

        TransactionDetails transactionDetails = getMockTransactionDetails();

        when(transactionDetailsRepository.findByOrderId(anyString())).thenReturn(Optional.of(transactionDetails));

        //Actual
        PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderId("1");

        //Verification
        verify(transactionDetailsRepository, times(1)).findByOrderId(anyString());

        //Assert
        assertNotNull(paymentResponse);
        assertEquals(transactionDetails.getId(), paymentResponse.getId());
    }


    private TransactionRequest getMockPaymentRequest() {
        return new TransactionRequest("1", 500.0, null, PaymentMode.CASH);

    }

    private TransactionDetails getMockTransactionDetails() {
        return TransactionDetails.builder()
                                 .id("1")
                                 .orderId("1")
                                 .paymentDate(Instant.now())
                                 .paymentMode(PaymentMode.CASH)
                                 .paymentStatus("SUCCESS")
                                 .referenceNumber(null)
                                 .amount(500)
                                 .build();
    }
}