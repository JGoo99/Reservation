package com.example.reservation.repository;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopDetailDto;
import com.example.reservation.data.entity.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

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

  @Test
  void findAllWithPageable() {
    // given
    Shop shop = shopRepository.save(ShopAddDto.toEntity(getShopAddDto()));
    Pageable pageable = getPaging(getSearchDto());

    // when
    Page<Shop> selected = shopRepository.findAll(pageable);
    Page<ShopDetailDto> list = selected.map(ShopDetailDto::fromEntity);

    // then
    list.forEach(m -> System.out.println(m.toString()));
  }

  @Test
  void findAllByShopNameContainingIgnoreCase() {
    // given
    Shop shop = shopRepository.save(ShopAddDto.toEntity(getShopAddDto()));
    Pageable pageable = getPaging(getSearchDto());

    // when
    Page<Shop> selected =
      shopRepository.findAllByShopNameContainingIgnoreCase(shop.getShopName(), pageable);
    Page<ShopDetailDto> list = selected.map(ShopDetailDto::fromEntity);

    // then
    list.forEach(m -> System.out.println(m.toString()));
  }

  @Test
  void delete() {
    // given
    Shop shop = shopRepository.save(ShopAddDto.toEntity(getShopAddDto()));

    // when
    shopRepository.deleteById(shop.getId());

    // then
    assertFalse(shopRepository.existsById(shop.getId()));
  }

  public PageRequest getPaging(SearchDto searchDto) {
    if (searchDto.getDirection() == 0) {
      return PageRequest.of(searchDto.getPageNum() - 1, searchDto.getSize(),
        Sort.by(ASC, searchDto.getDirectionColumn()));
    }
    return PageRequest.of(searchDto.getPageNum() - 1, searchDto.getSize(),
      Sort.by(DESC, searchDto.getDirectionColumn()));
  }

  SearchDto getSearchDto() {
    return new SearchDto();
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
