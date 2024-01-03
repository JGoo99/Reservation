package com.example.reservation.repository;

import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.entity.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ShopRepositoryTest {

  @Autowired
  private ShopRepository shopRepository;

  @Test
  void save() {
    // given
    ShopAddDto shopAddDto = getShopAddDto();

    // when
    Shop shop = shopRepository.save(ShopAddDto.toEntity(shopAddDto));

    // then
    assertEquals(shopAddDto.getShopName(), shop.getShopName());
    assertEquals(shopAddDto.getShopExplain(), shop.getShopExplain());
  }

  ShopAddDto getShopAddDto() {
    return ShopAddDto.builder()
      .shopName("스타벅스")
      .address1("강남")
      .address2("2층")
      .shopExplain("강남에 있는 스타벅스입니다.")
      .ownerId(1L)
      .build();
  }


  Shop getShop() {
    return Shop.builder()
      .shopName("스타벅스")
      .address1("강남")
      .address2("2층")
      .shopExplain("강남에 있는 스타벅스입니다.")
      .ownerId(1L)
      .stars(0)
      .build();
  }

}
