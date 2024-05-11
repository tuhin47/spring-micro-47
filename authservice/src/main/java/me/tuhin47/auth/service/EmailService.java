package me.tuhin47.auth.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
