package me.tuhin47.orderservice.controller;

import me.tuhin47.orderservice.dummy.TopOrderDtoDummy;
import me.tuhin47.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import me.tuhin47.payload.response.TopOrderDto;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void getTop10OrderByDateRange_shouldReturnTop10Orders() {
        // Given
        Instant startDate = Instant.now();
        Instant endDate = startDate.plusSeconds(60);
        List<TopOrderDto> expectedTop10Orders = Arrays.asList(TopOrderDtoDummy.getFirstTopOrderDto(), TopOrderDtoDummy.getSecondTopOrderDto());
        when(orderService.getTop10OrderByDateRange(any(Instant.class), any(Instant.class))).thenReturn(expectedTop10Orders);

        // When
        ResponseEntity<List<TopOrderDto>> responseEntity = orderController.getTop10OrderByDateRange(startDate, endDate);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedTop10Orders, responseEntity.getBody());
    }


}