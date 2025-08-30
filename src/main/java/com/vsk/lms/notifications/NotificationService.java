package com.vsk.lms.notifications;


import java.util.List;

public interface NotificationService {
    void sendEmail(String to, String subject, String body);
}


