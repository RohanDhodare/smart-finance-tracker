package com.rohan.finance_tracker.auth;

import com.rohan.finance_tracker.auth.dto.LoginRequest;
import com.rohan.finance_tracker.auth.dto.SignupRequest;
import com.rohan.finance_tracker.exception.InvalidCredsException;
import com.rohan.finance_tracker.exception.UsernameAlreadyExistsException;
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

    public User saveUser(SignupRequest userDetails){
        User user = new User();
        if(userRepository.existsByUsername(userDetails.getUsername())){
            throw new UsernameAlreadyExistsException("Username already exists. Please use different one");
        }
        else{
            user.setName(userDetails.getName());
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            userRepository.save(user);
        }
        return user;
    }

    public User loginUser(LoginRequest loginDetails){
        User user;
        if(!userRepository.existsByUsername(loginDetails.getUsername())){
            throw new InvalidCredsException("Invalid username");
        }
        else{
            user = userRepository.findByUsername(loginDetails.getUsername());
            if(!loginDetails.getPassword().equals(user.getPassword())){
                throw new InvalidCredsException("Invalid password");
            }
        }
        return user;
    }
}
