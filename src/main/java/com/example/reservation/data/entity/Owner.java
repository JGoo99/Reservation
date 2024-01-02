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
@Table(name = "owner")
@ToString(callSuper = true)
public class Owner extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String busNumber; // business number

  @Column(unique = true)
  private String email;

  private String ownerName;
  private String password;
  private String phone;
  private String address;
  private String role;

  @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
  @ToString.Exclude
  @Builder.Default
  private List<Review> reviews = new ArrayList<>();
}
