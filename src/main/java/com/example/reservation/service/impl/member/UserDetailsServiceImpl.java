package com.example.reservation.service.impl.member;

import com.example.reservation.data.dto.user.CustomUserDetails;
import com.example.reservation.data.entity.User;
import com.example.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);

    if (user.isPresent()) {
      return new CustomUserDetails(user.get());
    }

    return null;
  }
}
