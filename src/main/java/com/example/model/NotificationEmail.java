package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationEmail {
    private String recipient;
    private String subject;
    private String body;
}
