package com.hof.yakomozauth.repository;

import com.hof.yakomozauth.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByUsername(String username);
    boolean existsByUsername(String username);

}
