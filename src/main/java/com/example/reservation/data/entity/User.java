package com.example.reservation.data.entity;

import com.example.reservation.data.dto.join.UserJoinDto;
import com.example.reservation.type.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String email;

  private String password;
  private String username;
  private String phone;
  private String address;
  private RoleType role;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  @ToString.Exclude
  @Builder.Default
  private List<Review> reviews = new ArrayList<>();

  public static User toEntity(UserJoinDto userJoinDto) {
    return User.builder()
      .email(userJoinDto.getEmail())
      .password(userJoinDto.getPassword())
      .username(userJoinDto.getUsername())
      .phone(userJoinDto.getPhone())
      .address(userJoinDto.getAddress())
      .role(RoleType.ROLE_USER)
      .build();
  }
}
