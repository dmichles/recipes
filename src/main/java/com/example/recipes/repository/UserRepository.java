package com.example.recipes.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.recipes.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findUserByEmail(String email);
    public User findUserByUserid(Long userid);
}
