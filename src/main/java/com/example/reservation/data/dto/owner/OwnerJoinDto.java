package com.example.reservation.data.dto.owner;

import com.example.reservation.data.entity.Owner;
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
public class OwnerJoinDto {

  @NotBlank
  @Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{5}$")
  private String busNumber;

  @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
  @NotBlank
  private String email;

  @NotBlank
  private String password;

  @NotNull
  private String ownerName;

  @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
  private String phone;

  private String address;

  public static Owner toEntity(OwnerJoinDto ownerJoinDto) {
    return Owner.builder()
      .busNumber(ownerJoinDto.getBusNumber())
      .ownerName(ownerJoinDto.getOwnerName())
      .email(ownerJoinDto.getEmail())
      .password(ownerJoinDto.getPassword())
      .phone(ownerJoinDto.getPhone())
      .address(ownerJoinDto.getAddress())
      .role(RoleType.ROLE_OWNER.toString())
      .build();
  }

  public static OwnerJoinDto from(Owner owner) {
    return OwnerJoinDto.builder()
      .busNumber(owner.getBusNumber())
      .email(owner.getEmail())
      .ownerName(owner.getOwnerName())
      .phone(owner.getPhone())
      .address(owner.getAddress())
      .build();
  }
}
