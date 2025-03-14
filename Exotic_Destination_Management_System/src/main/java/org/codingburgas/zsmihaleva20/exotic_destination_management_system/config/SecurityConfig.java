package org.codingburgas.zsmihaleva20.exotic_destination_management_system.config;

import org.codingburgas.zsmihaleva20.exotic_destination_management_system.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
                        .requestMatchers("/register", "/css/**", "/js/**", "/images/**", "/", "/uploaded-image/**", "/home").permitAll()
                        .requestMatchers("/destinationManagement", "reservationManagement", "/addDestination", "/editDestination/", "ratingManagement").hasAnyAuthority("ADMIN", "MANAGER") // Allow both ADMIN and MANAGER
                        .requestMatchers("/profilesManagement").hasAuthority("ADMIN") // Only ADMIN for adminProfiles
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/destinations", "promotions", "myReservations", "reservation", "/rateDestination", "/profile", "statistics").authenticated()
                        .requestMatchers("/rateDestination", "/reservation/").hasAnyAuthority("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/destinations", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
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

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}

