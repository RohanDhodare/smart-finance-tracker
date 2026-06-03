package com.rohan.finance_tracker.auth;

import com.rohan.finance_tracker.auth.dto.LoginRequest;
import com.rohan.finance_tracker.auth.dto.SignupRequest;
import com.rohan.finance_tracker.user.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> registerUser(@Valid @RequestBody SignupRequest userDetails){
//        logger.info("The user details are : {}", userDetails);
        User savedUser = authService.saveUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/login")
    public ResponseEntity<User> loginUser(@Valid @RequestBody LoginRequest loginDetails){
        User loggedinUser = authService.loginUser(loginDetails);
        return ResponseEntity.status(HttpStatus.OK).body(loggedinUser);
    }

}
