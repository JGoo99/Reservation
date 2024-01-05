package com.example.reservation.data.dto.shop;

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
public class ShopInfoDto {

  @NotBlank
  private String shopName;

  @NotNull
  private String address1;

  @NotNull
  private String address2;

  private String shopExplain;

  private int stars;

  private int reviewCount;

  private Long shopId;
  private Long ownerId;

  public static ShopInfoDto fromEntity(Shop shop) {
    return ShopInfoDto.builder()
      .shopName(shop.getShopName())
      .address1(shop.getAddress1())
      .address2(shop.getAddress2())
      .shopExplain(shop.getShopExplain())
      .stars(shop.getStars())
      .reviewCount(shop.getReviewCount())
      .shopId(shop.getId())
      .ownerId(shop.getOwnerId())
      .build();
  }

  public static Shop toEntity(ShopInfoDto shopInfoDto) {
    return Shop.builder()
      .shopName(builder().shopName)
      .address1(shopInfoDto.getAddress1())
      .address2(shopInfoDto.getAddress2())
      .shopExplain(shopInfoDto.getShopExplain())
      .stars(shopInfoDto.getStars())
      .reviewCount(shopInfoDto.getReviewCount())
      .ownerId(shopInfoDto.getOwnerId())
      .build();
  }
}
