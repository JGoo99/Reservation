package com.example.reservation.service.impl.main;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
import com.example.reservation.data.entity.Shop;
import com.example.reservation.repository.ShopRepository;
import com.example.reservation.service.inter.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

  private final ShopRepository shopRepository;

  /**
   * 매장 정보 등록 메소드
   */
  @Override
  public ShopInfoDto add(ShopAddDto shopAddDto) {

    validateDuplicateStore(shopAddDto.getShopName());
    Shop shop = shopRepository.save(ShopAddDto.toEntity(shopAddDto));

    return ShopInfoDto.fromEntity(shop);
  }

  /**
   * 해당 점장이 등록한 매장 리스트를 반환
   */
  @Override
  public List<ShopInfoDto> getOwnerShopList(Long ownerId) {
    List<Shop> shops = shopRepository.findAllByOwnerId(ownerId);

    List<ShopInfoDto> shopInfoList = new ArrayList<>();
    for (Shop s : shops) {
      shopInfoList.add(ShopInfoDto.fromEntity(s));
    }

    return shopInfoList;
  }

  /**
   * 전체 매장리스트
   * - 유저와 점장의 메인페이지에서 매장 리스트를 페이징 처리하여 보여줄 때 사용
   * @param searchDto .
   * @return 등록된 매장리스틀 페이징 처리하여 반환
   */
  @Override
  public Page<ShopInfoDto> getSearchedShopList(SearchDto searchDto) {
    Pageable pageable = getPaging(searchDto);

    Page<Shop> selectedShops = null;
    if (searchDto.getKeyword() == null) {
      selectedShops = shopRepository.findAll(pageable);
    } else {
      selectedShops = shopRepository.findAllByShopNameContainingIgnoreCase(searchDto.getKeyword(), pageable);
    }

    return selectedShops.map(ShopInfoDto::fromEntity);
  }

  @Override
  public ShopInfoDto getShopInfo(Long shopId) {
    Shop shop = shopRepository.findById(shopId)
      .orElseThrow(() -> new RuntimeException("해당 매장의 정보가 존재하지 않습니다."));

    return ShopInfoDto.fromEntity(shop);
  }

  /**
   * 매장 삭제 메소드
   */
  @Override
  public boolean delete(Long shopId) {
    shopRepository.deleteById(shopId);
    // 관련 예약, 리뷰 db 수정 필요

    if(shopRepository.existsById(shopId)) {
      throw new RuntimeException("매장정보가 유효하지 않아 매장 삭제에 실패했습니다.");
    }
    return true;
  }

  /**
   * 매장 정보 수정 메소드
   */
  @Override
  public ShopInfoDto edit(ShopInfoDto shopInfoDto) {
    Shop shop = shopRepository.findById(shopInfoDto.getShopId())
      .orElseThrow(() -> new RuntimeException("매장정보가 유효하지 않아 매장 정보 수정에 실패했습니다."));

    Shop savedShop = shopRepository.save(editEntityByDto(shop, shopInfoDto));

    return ShopInfoDto.fromEntity(savedShop);
  }

  public Shop editEntityByDto(Shop shop, ShopInfoDto shopInfoDto) {
    shop.setShopName(shopInfoDto.getShopName());
    shop.setAddress1(shopInfoDto.getAddress1());
    shop.setAddress2(shopInfoDto.getAddress2());
    shop.setShopExplain(shopInfoDto.getShopExplain());
    shop.setOpen(shopInfoDto.getOpen());
    shop.setClose(shopInfoDto.getClose());

    return shop;
  }

  /**
   * 매장명 : unique = true
   */
  public void validateDuplicateStore(String storeName) {
    boolean isStore = shopRepository.existsByShopName(storeName);
    if (isStore) {
      throw new RuntimeException("이미 등록된 매장입니다.");
    }
  }

  /**
   * 페이징 구현체를 반환하는 메소드
   * - 오름차순 / 내림차순에 따라 반환하는 구현체가 다름
   * @param searchDto 유저, 점장의 메인화면에서 받아온 페이징 및 검색 관련 dto
   */
  public PageRequest getPaging(SearchDto searchDto) {
    if (searchDto.getDirection() == 0) {
      return PageRequest.of(searchDto.getPageNum() - 1, searchDto.getSize(),
        Sort.by(ASC, searchDto.getDirectionColumn()));
    }
    return PageRequest.of(searchDto.getPageNum() - 1, searchDto.getSize(),
      Sort.by(DESC, searchDto.getDirectionColumn()));
  }
}
