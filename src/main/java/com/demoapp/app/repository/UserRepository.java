package com.demoapp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demoapp.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

}
