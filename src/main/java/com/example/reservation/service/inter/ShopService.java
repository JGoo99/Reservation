package com.example.reservation.service.inter;

import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopDetailDto;

import java.util.List;

public interface ShopService {
  ShopDetailDto add(ShopAddDto storeDto, String ownerEmail);

  List<ShopDetailDto> getShopList(Long ownerId);

  ShopDetailDto getShopDetails(Long id);
}
