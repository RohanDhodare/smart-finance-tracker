package com.rohan.finance_tracker.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    we just add method and JPA creates implementation on it's own
    boolean existsByUsername(String username);
}
