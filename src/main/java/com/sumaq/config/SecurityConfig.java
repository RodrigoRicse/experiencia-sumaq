package com.sumaq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/", "/menu", "/carrito/**", "/pedido/**",
                                "/css/**", "/js/**", "/img/**", "/favicon.ico",
                                "/actuator/health")
                        .permitAll()
                        .requestMatchers("/admin/**", "/actuator/info", "/actuator/metrics/**")
                        .hasRole("ADMINISTRADOR")
                        .requestMatchers("/cocina/**").hasRole("COCINA")
                        .requestMatchers("/caja/**").hasRole("CAJA")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
