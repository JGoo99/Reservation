package com.example.reservation.data.dto.join;

import com.example.reservation.data.entity.User;
import com.example.reservation.type.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserJoinDto {

  @NotBlank
  @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
  private String email;

  @NotBlank
  private String password;

  @NotNull
  private String username;
  @NotNull
  private String nickname;

  @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
  private String phone;

  private String address;

  public static User toEntity(UserJoinDto userJoinDto) {
    return User.builder()
      .email(userJoinDto.getEmail())
      .password(userJoinDto.getPassword())
      .username(userJoinDto.getUsername())
      .nickname(userJoinDto.getNickname())
      .phone(userJoinDto.getPhone())
      .address(userJoinDto.getAddress())
      .role(String.valueOf(RoleType.ROLE_USER))
      .build();
  }
}
