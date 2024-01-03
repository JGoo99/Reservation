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
public class ShopDetailDto {

  @NotBlank
  private String shopName;

  @NotNull
  private String address1;

  @NotNull
  private String address2;

  private String shopExplain;

  private double stars;

  private int reviewCount;

  public static ShopDetailDto fromEntity(Shop shop) {
    return ShopDetailDto.builder()
      .shopName(shop.getShopName())
      .address1(shop.getAddress1())
      .address2(shop.getAddress2())
      .shopExplain(shop.getShopExplain())
      .stars(shop.getStars())
      .reviewCount(shop.getReviewCount())
      .build();
  }
}
