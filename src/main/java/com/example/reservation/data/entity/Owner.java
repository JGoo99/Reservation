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
@Table(name = "owner")
public class Owner extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String busNumber; // business number

  @Column(unique = true)
  private String email;

  private String password;
  private String ownerName;
  private String phone;
  private String stAddress1; // store address
  private String stAddress2;
  private RoleType role;

  @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
  @ToString.Exclude
  @Builder.Default
  private List<Review> reviews = new ArrayList<>();
}
