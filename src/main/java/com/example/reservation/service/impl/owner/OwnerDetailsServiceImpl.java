package com.example.reservation.service.impl.owner;

import com.example.reservation.data.dto.login.OwnerDetails;
import com.example.reservation.data.entity.Owner;
import com.example.reservation.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerDetailsServiceImpl implements UserDetailsService {

  @Autowired
  OwnerRepository ownerRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Owner> owner = ownerRepository.findByEmail(email);

    if (owner.isPresent()) {
      return new OwnerDetails(owner.get());
    }

    return null;
  }
}
