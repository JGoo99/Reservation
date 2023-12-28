package com.example.reservation.data.entity;

import com.example.reservation.type.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  private String password;
  private String phone;
  private String stAddress1;
  private String stAddress2;
  private RoleType role;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  @ToString.Exclude
  private List<Review> reviews = new ArrayList<>();
}
