package com.example.pervage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/css/**", "/js/**").permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .defaultSuccessUrl("/index.html", true)
                                                .permitAll())
                                .logout(logout -> logout.permitAll());

                return http.build();
        }
}
