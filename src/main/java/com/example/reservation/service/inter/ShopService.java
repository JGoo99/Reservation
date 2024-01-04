package com.example.reservation.service.inter;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopDetailDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShopService {
  ShopDetailDto add(ShopAddDto storeDto, String ownerEmail);

  List<ShopDetailDto> getOwnerShopList(Long ownerId);

  Page<ShopDetailDto> getSearchedShopList(SearchDto searchDto);

  ShopDetailDto getOwnerShopDetails(Long shopId);

  boolean delete(Long shopId);

  ShopDetailDto edit(ShopDetailDto shopDetailDto);
}
