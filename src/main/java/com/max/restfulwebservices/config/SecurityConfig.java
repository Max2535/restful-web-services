package com.max.restfulwebservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"),new AntPathRequestMatcher("/actuator/health"),new AntPathRequestMatcher("/actuator/info")).permitAll() // ระบุให้ H2 Console ใช้ AntPathRequestMatcher
                        .anyRequest().authenticated() // Authentication สำหรับ Endpoint อื่น
                )
                .httpBasic(Customizer.withDefaults()); // ใช้ Basic Authentication

        // ปิดการป้องกัน Frame Options เพื่อรองรับ H2 Console
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ใช้ BCrypt สำหรับการเข้ารหัส Password
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}


/*
*
* @Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

@Override
protected void configure(HttpSecurity http) throws Exception {
http.authorizeRequests()
.antMatchers("/actuator/health", "/actuator/info").permitAll()
.antMatchers("/actuator/**").authenticated()
.and()
.httpBasic();
}
}
*
* */