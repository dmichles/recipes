package com.example.recipes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(userDetailsService) // user store 1
                .passwordEncoder(getEncoder());

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/actuator/shutdown").permitAll() // (1)
                .mvcMatchers("/api/register").permitAll()
                .mvcMatchers("/api/recipe/**").authenticated()
                .and()
                .formLogin() // (2)
                .and()
                .csrf().disable()
                .httpBasic();// (3)
    }


}
