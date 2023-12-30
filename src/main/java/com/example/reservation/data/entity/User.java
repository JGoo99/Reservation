package com.example.reservation.data.entity;

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
@ToString(callSuper = true)
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String email;

  private String username;
  private String nickname;
  private String password;
  private String phone;
  private String address;
  private String role;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  @ToString.Exclude
  @Builder.Default
  private List<Review> reviews = new ArrayList<>();
}
