package com.project.board.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()	//기본 로그인 페이지 없애기
                .headers().frameOptions().disable();
    }

    @Bean
    public BCryptPasswordEncoder encodePasswrod(){
        return new BCryptPasswordEncoder();
    }
}
