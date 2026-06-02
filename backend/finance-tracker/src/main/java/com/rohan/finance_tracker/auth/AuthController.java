package com.rohan.finance_tracker.auth;

import com.rohan.finance_tracker.auth.dto.SignupRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

//    Logger created for this class
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;

    //    Dependency injection
    AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody SignupRequest userDetails){
        logger.info("The user details are : {}", userDetails);
        authService.saveUser(userDetails);
        return "User Registered successfully";
    }

    @GetMapping("/login")
    public String loginUser(){
        return "User Logged in succesfully!!";
    }
}
