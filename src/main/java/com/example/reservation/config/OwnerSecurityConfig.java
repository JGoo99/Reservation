package com.example.reservation.config;

import com.example.reservation.service.impl.owner.OwnerDetailsServiceImpl;
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
@Order(2)
public class OwnerSecurityConfig {

  @Bean
  public UserDetailsService ownerDetailsService() {
    return new OwnerDetailsServiceImpl();
  }

  @Bean
  public BCryptPasswordEncoder ownerBCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider ownerAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(ownerDetailsService());
    provider.setPasswordEncoder(ownerBCryptPasswordEncoder());

    return provider;
  }

  @Bean
  public SecurityFilterChain ownerSecurityFilterChain(HttpSecurity http) throws Exception {

    http
      .csrf((auth) -> auth.disable());

    http
      .authenticationProvider(ownerAuthenticationProvider())
      .authorizeHttpRequests((auth) -> auth
        .anyRequest().permitAll()
      );

    http
      .formLogin((auth) -> auth
        .loginPage("/owner/login")
        .loginProcessingUrl("/owner/loginProc")
        .usernameParameter("email")
        .defaultSuccessUrl("/owner/home")
      )
      .logout((auth) -> auth
        .logoutUrl("/owner/logout")
        .logoutSuccessUrl("/owner/home")
      );

    return http.build();
  }

}
