package com.rohan.finance_tracker.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {

    @NotBlank(message = "Name shouldn't be blank")
    @Size(min = 2, message = "Name should be at least of length 2")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupRequest{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
