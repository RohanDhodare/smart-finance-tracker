package com.rohan.finance_tracker.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    we just add method and JPA creates implementation on it's own
    boolean existsByUsername(String username);

//    to get User details from DB
    Optional<User> findByUsername(String username);
}
