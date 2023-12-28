package com.example.reservation.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review")
public class Review extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String content;
  private int star;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Owner owner;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
