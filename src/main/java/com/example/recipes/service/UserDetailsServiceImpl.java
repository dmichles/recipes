package com.example.recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.recipes.model.User;
import com.example.recipes.model.UserDetailsImpl;
import com.example.recipes.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findUserByEmail(email);

        if (user == null) {
            System.out.println("here1");
            throw new UsernameNotFoundException("Not found: " + email);
        }

        return new UserDetailsImpl(user);
    }
}
