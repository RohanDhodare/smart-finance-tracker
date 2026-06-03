package com.rohan.finance_tracker.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Username shouldn't be blank")
    @Size(min = 5, message = "Username should be at least of length 5")
    private String username;

    @NotBlank(message = "Password shouldn't be blank")
    @Size(min = 8, message = "Minimum required length for password is 8")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
