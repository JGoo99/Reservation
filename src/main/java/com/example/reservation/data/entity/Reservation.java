package com.example.reservation.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation")
public class Reservation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private boolean isVisited;
  private int isAccepted; // | -1 거절 | 0 대기 | 1 승인 |

  private LocalDateTime reservedAt; // 예약시간 (이용시간은 1시간 단위로 고정)
  private int time; // 이용 시간

  private String userName;
  private String userPhone;
  private Long userId;
  private Long shopId;
}
