package com.example.reservation.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shop")
@ToString(callSuper = true)
public class Shop extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String shopName;

  private String address1;
  private String address2;

  private String shopExplain;

  private int stars; // 총 별점
  private int reviewCount;

  private int open;
  private int close;

  private Long ownerId;
}
