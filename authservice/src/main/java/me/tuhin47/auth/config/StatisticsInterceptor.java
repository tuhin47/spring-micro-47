package me.tuhin47.auth.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.service.StatisticsService;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class StatisticsInterceptor implements HandlerInterceptor {

    private final StatisticsService statisticsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        statisticsService.incrementRequests();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        if (request.getRequestURI().equals("/auth/signin") && response.getStatus() == HttpStatus.SC_OK) {
            statisticsService.userLoggedIn();
        }
    }
}