package com.example.util;

import lombok.experimental.UtilityClass;

@UtilityClass // makes class final, generates a private non-args constructor, fields can only be static
public class Constants {
    public static final String ACTIVATION_EMAIL = "http://localhost:8080/api/auth/accountVerification";
}
