package com.athletic.api;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeTests {
    @Test
    void encode() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pw = "1";
        System.out.println("encoded password : " + passwordEncoder.encode(pw));

    }
}
