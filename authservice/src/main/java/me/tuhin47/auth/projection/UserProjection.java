package me.tuhin47.auth.projection;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.payload.mapper.UserMapper;
import me.tuhin47.auth.repo.UserRepository;
import me.tuhin47.payload.response.UserResponse;
import me.tuhin47.saga.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProjection {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @QueryHandler
    public UserResponse getUserResponse(GetUserPaymentDetailsQuery query) {
        return userMapper.toDto(userRepository.getReferenceById(query.getUserId()));
    }
}
