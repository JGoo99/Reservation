package com.example.reservation.service.inter;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShopService {
  ShopInfoDto add(ShopAddDto storeDto);

  List<ShopInfoDto> getOwnerShopList(Long ownerId);

  Page<ShopInfoDto> getSearchedShopList(SearchDto searchDto);

  ShopInfoDto getShopInfo(Long shopId);

  boolean delete(Long shopId);

  ShopInfoDto edit(ShopInfoDto shopInfoDto);
}
