package me.tuhin47.config.utils;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.payload.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ComputableFutureRequestHandler {

    private final Tracer tracer;
    private List<CompletableFuture> completableFutures;

    public void init() {
        completableFutures = new ArrayList<>();
    }

    public <T extends BaseResponse> CompletableFuture<ResponseEntity<T>> addResponse(Supplier<ResponseEntity<T>> response) {
        if (completableFutures == null) {
            this.init();
        }
        CompletableFuture<ResponseEntity<T>> computableFutureResponse = getComputableFutureResponse(response);
        completableFutures.add(computableFutureResponse);
        return computableFutureResponse;
    }


    public List<BaseResponse> getResponseList() {
        return getResponseList(HttpStatus.OK);
    }

    public List<BaseResponse> getResponseList(HttpStatus status) {
        joinAll();
        List<BaseResponse> result = completableFutures.stream().map(future -> getResponseFromResponseEntity(future, status))
                                                      .collect(Collectors.toList());
        destroy();
        return result;
    }

    public void joinAll() {
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
    }

    public void destroy() {
        completableFutures = null;
    }

    public static BaseResponse getResponseFromResponseEntity(CompletableFuture<ResponseEntity<BaseResponse>> responseFuture, HttpStatus status) {
        ResponseEntity<BaseResponse> entity = responseFuture.join();
        return entity.getStatusCode() == status ? entity.getBody() : null;
    }

    private <T extends BaseResponse> CompletableFuture<ResponseEntity<T>> getComputableFutureResponse(Supplier<ResponseEntity<T>> response) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Span span = tracer.currentSpan();
        return CompletableFuture.supplyAsync(() -> {
            try (Tracer.SpanInScope spanInScope = tracer.withSpan(span)) {
                log.debug("setting request attributes");
                RequestContextHolder.setRequestAttributes(requestAttributes);
                return response.get();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        });
    }
}
