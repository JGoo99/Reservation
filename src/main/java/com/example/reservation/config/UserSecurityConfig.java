package com.example.reservation.config;

import com.example.reservation.service.impl.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1)
public class UserSecurityConfig {

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider userAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(bCryptPasswordEncoder());

    return provider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
      .csrf((auth) -> auth.disable());

    http
      .authenticationProvider(userAuthenticationProvider())
      .authorizeHttpRequests((auth) -> auth
        .requestMatchers("/home").hasRole("USER")
        .anyRequest().permitAll()
      );

    http
      .formLogin((auth) -> auth
        .loginPage("/login")
        .loginProcessingUrl("/loginProc")
        .usernameParameter("email")
        .defaultSuccessUrl("/home")
        .permitAll()
      )
      .logout((auth) -> auth
        .logoutUrl("/logout")
        .logoutSuccessUrl("/home")
      );

    return http.build();
  }
}
