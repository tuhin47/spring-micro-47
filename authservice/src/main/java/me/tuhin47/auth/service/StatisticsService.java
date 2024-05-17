package me.tuhin47.auth.service;

public interface StatisticsService {
    void incrementRequests();

    void userLoggedIn();

    void userLoggedOut();

    int getTotalRequests();

    int getCurrentUsersOnline();
}