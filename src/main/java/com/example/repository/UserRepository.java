package com.example.repository;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tinoll on 2017. 1. 9..
 */
public interface UserRepository extends JpaRepository<User, String> {

}
