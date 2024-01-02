package com.example.reservation.service.inter.shop;

import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopDetailDto;

public interface ShopService {
  ShopDetailDto add(ShopAddDto storeDto, String ownerEmail);
}
