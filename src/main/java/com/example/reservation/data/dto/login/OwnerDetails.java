package com.example.reservation.data.dto.login;

import com.example.reservation.data.entity.Owner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class OwnerDetails implements UserDetails {

  private Owner owner;

  public OwnerDetails(Owner owner) {
    this.owner = owner;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Collection<GrantedAuthority> collection = new ArrayList<>();
    collection.add(new GrantedAuthority() {
      @Override
      public String getAuthority() {
        return owner.getRole();
      }
    });

    return collection;
  }

  @Override
  public String getPassword() {
    return owner.getPassword();
  }

  @Override
  public String getUsername() {
    return owner.getOwnerName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
