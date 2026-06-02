package com.rohan.finance_tracker.auth;

import com.rohan.finance_tracker.auth.dto.SignupRequest;
import com.rohan.finance_tracker.user.User;
import com.rohan.finance_tracker.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;

//    Dependency injection
    AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void saveUser(SignupRequest userDetails){
        User user = new User();

        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already exists. Please use different one");
        }
        else{
            user.setName(userDetails.getName());
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            userRepository.save(user);
        }
    }
}
