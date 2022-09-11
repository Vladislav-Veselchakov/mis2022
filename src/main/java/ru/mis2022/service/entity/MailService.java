package ru.mis2022.service.entity;

public interface MailService {
    void send(String mailTo, String subject, String message);
}
