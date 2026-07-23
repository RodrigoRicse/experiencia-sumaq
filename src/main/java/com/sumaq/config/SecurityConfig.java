package com.sumaq.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(
                                "/", "/menu", "/carrito/**", "/pedido/**", "/login",
                                "/css/**", "/js/**", "/img/**", "/favicon.ico",
                                "/actuator/health")
                        .permitAll()
                        .requestMatchers("/admin/**", "/actuator/info", "/actuator/metrics/**")
                        .hasRole("ADMINISTRADOR")
                        .requestMatchers("/cocina/**").hasAnyRole("COCINA", "ADMINISTRADOR")
                        .requestMatchers("/caja/**").hasAnyRole("CAJA", "ADMINISTRADOR")
                        .anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/error/403"))
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/personal", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
