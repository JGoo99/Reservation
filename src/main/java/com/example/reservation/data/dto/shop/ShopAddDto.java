package com.example.reservation.data.dto.shop;

import com.example.reservation.data.entity.Owner;
import com.example.reservation.data.entity.Shop;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ShopAddDto {

  @NotBlank
  private String shopName;

  @NotBlank
  private Owner owner;

  @NotNull
  private String address1;

  @NotNull
  private String address2;

  private String shopExplain;

  public static Shop toEntity(ShopAddDto shopAddDto) {
    return Shop.builder()
      .shopName(shopAddDto.getShopName())
      .address1(shopAddDto.getAddress1())
      .address2(shopAddDto.getAddress2())
      .shopExplain(shopAddDto.getShopExplain())
      .stars(0)
      .owner(shopAddDto.getOwner())
      .build();
  }

}
