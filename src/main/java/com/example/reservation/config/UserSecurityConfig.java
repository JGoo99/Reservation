package com.example.reservation.config;

import com.example.reservation.service.impl.member.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  public PasswordEncoder userBCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider userAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(userBCryptPasswordEncoder());

    return provider;
  }

  @Bean
  public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {

    http
      .csrf((auth) -> auth.disable());

    http
      .authenticationProvider(userAuthenticationProvider())
      .securityMatchers((matchers) -> matchers.requestMatchers("/**"))
      .authorizeHttpRequests((auth) -> auth
        .requestMatchers("/user/home").permitAll()
        .requestMatchers("/user/**").hasRole("USER")
        .anyRequest().permitAll()
      );

    http
      .formLogin((auth) -> auth
        .loginPage("/login")
        .loginProcessingUrl("/loginProc")
        .usernameParameter("email")
        .defaultSuccessUrl("/user/home")
      )
      .logout((auth) -> auth
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID")
      );

    return http.build();
  }
}
