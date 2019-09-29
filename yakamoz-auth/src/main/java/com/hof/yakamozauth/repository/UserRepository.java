package com.hof.yakamozauth.repository;

import com.hof.yakamozauth.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByUsername(String username);
    boolean existsByUsername(String username);

}
