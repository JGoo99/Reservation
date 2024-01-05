package com.example.reservation.data.dto.shop;

import com.example.reservation.data.entity.Shop;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
  private Long ownerId;

  @NotNull
  private String address1;

  @NotNull
  private String address2;

  @NotBlank
  @Min(0) @Max(24)
  private int open;

  @NotBlank
  @Min(0) @Max(24)
  private int close;

  private String shopExplain;

  public static Shop toEntity(ShopAddDto shopAddDto) {
    return Shop.builder()
      .shopName(shopAddDto.getShopName())
      .address1(shopAddDto.getAddress1())
      .address2(shopAddDto.getAddress2())
      .shopExplain(shopAddDto.getShopExplain())
      .stars(0)
      .reviewCount(0)
      .open(shopAddDto.getOpen())
      .close(shopAddDto.getClose())
      .ownerId(shopAddDto.getOwnerId())
      .build();
  }

}
