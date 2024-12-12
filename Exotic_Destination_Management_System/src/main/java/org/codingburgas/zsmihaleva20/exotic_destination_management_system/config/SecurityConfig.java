package org.codingburgas.zsmihaleva20.exotic_destination_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signUp", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers( "/css/**", "/js/**", "/images/**", "/destinationManagement").hasRole("MANAGER")
                        .requestMatchers("/adminProfiles", "/css/**", "/js/**", "/images/**").hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home",true)
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        userDetailsManager.createUser(User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build());
        userDetailsManager.createUser(User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build());
        userDetailsManager.createUser(User.builder()
                .username("manager")
                .password(passwordEncoder().encode("manager"))
                .roles("MANAGER")
                .build());
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void addUser(String username, String password, String... roles) {
        userDetailsManager.createUser(User.builder()
                .username(username)
                .password(passwordEncoder().encode(password))
                .roles(roles)
                .build());
    }
}

