package me.tuhin47.auth.projection;

import me.tuhin47.core.payment.CardDetails;
import me.tuhin47.core.payment.UserResponse;
import me.tuhin47.saga.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public UserResponse getUserPaymentDetails(GetUserPaymentDetailsQuery query) {
        // TODO Ideally Get the details from the DB

        CardDetails cardDetails
                = CardDetails.builder()
                .name("Towhidul Islam")
                .validUntilYear(2022)
                .validUntilMonth(1)
                .cardNumber("123456789")
                .cvv(111)
                .build();

        return UserResponse.builder()
                .userId(query.getUserId())
                .firstName("Towhidul")
                .lastName("Islam")
                .cardDetails(cardDetails)
                .build();
    }
}
