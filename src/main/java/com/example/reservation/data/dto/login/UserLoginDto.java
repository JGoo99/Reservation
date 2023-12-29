package com.example.reservation.data.dto.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
public class UserLoginDto {
  private String email;
  private String password;
}
