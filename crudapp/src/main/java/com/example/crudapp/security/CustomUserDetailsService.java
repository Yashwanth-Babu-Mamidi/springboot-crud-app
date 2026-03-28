package com.example.crudapp.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class CustomUserDetailsService
        implements UserDetailsService {

    @Override

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        if(!username.equals("admin")){

            throw new UsernameNotFoundException(
                    "User not found"
            );

        }

        return new User(
                "admin",
                "$2a$10$.Hy4Ii40DOTM2okPt6ghqOt/9Sfky8jLedP.RLeeQr8ST60CFQHgC",
                Collections.emptyList()
        );
    }

}