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
public class ShopDetailDto {

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

  public static ShopDetailDto fromEntity(Shop shop) {
    return ShopDetailDto.builder()
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

  public static Shop toEntity(ShopDetailDto shopDetailDto) {
    return Shop.builder()
      .shopName(builder().shopName)
      .address1(shopDetailDto.getAddress1())
      .address2(shopDetailDto.getAddress2())
      .shopExplain(shopDetailDto.getShopExplain())
      .stars(shopDetailDto.getStars())
      .reviewCount(shopDetailDto.getReviewCount())
      .ownerId(shopDetailDto.getOwnerId())
      .build();
  }
}
