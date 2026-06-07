package com.rohan.finance_tracker.auth;

import com.rohan.finance_tracker.auth.dto.LoginRequest;
import com.rohan.finance_tracker.auth.dto.SignupRequest;
import com.rohan.finance_tracker.config.SecurityConfig;
import com.rohan.finance_tracker.exception.InvalidCredsException;
import com.rohan.finance_tracker.exception.UsernameAlreadyExistsException;
import com.rohan.finance_tracker.jwt.JwtService;
import com.rohan.finance_tracker.user.User;
import com.rohan.finance_tracker.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;
    private JwtService jwtService;
    private SecurityConfig securityConfig;

//    Dependency injection
    AuthService(UserRepository userRepository, SecurityConfig securityConfig,
                JwtService jwtService){
        this.userRepository = userRepository;
        this.securityConfig= securityConfig;
        this.jwtService = jwtService;
    }

    public User saveUser(SignupRequest userDetails){
        User user = new User();
        if(userRepository.existsByUsername(userDetails.getUsername())){
            throw new UsernameAlreadyExistsException("Username already exists. Please use different one");
        }
        else{
            String hashedPassword = securityConfig.passwordEncoder().encode(userDetails.getPassword());
            user.setName(userDetails.getName());
            user.setUsername(userDetails.getUsername());
            user.setPassword(hashedPassword);
            userRepository.save(user);
        }
        return user;
    }

    public String loginUser(LoginRequest loginDetails){
        User user;
        if(!userRepository.existsByUsername(loginDetails.getUsername())){
            throw new InvalidCredsException("Invalid username");
        }
        else{
            user = userRepository.findByUsername(loginDetails.getUsername());
//            below code was used for basic comparison
//            if(!loginDetails.getPassword().equals(user.getPassword())){
//                throw new InvalidCredsException("Invalid password");
//            }

            if(!securityConfig.passwordEncoder().matches(loginDetails.getPassword(), user.getPassword())){
                throw new InvalidCredsException("Invalid Password");
            }
        }
        return jwtService.generateToken(user.getUsername());
    }
}
