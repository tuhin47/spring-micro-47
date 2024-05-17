package me.tuhin47.auth.service.impl;

import lombok.Getter;
import me.tuhin47.auth.service.StatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Getter
@Service
@ApplicationScope
public class StatisticsServiceImpl implements StatisticsService {
    private int totalRequests;
    private int currentUsersOnline;

    public synchronized void incrementRequests() {
        totalRequests++;
    }

    public synchronized void userLoggedIn() {
        currentUsersOnline++;
    }

    public synchronized void userLoggedOut() {
        currentUsersOnline--;
    }

}