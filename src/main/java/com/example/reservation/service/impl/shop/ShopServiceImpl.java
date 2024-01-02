package com.example.reservation.service.impl.shop;

import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopDetailDto;
import com.example.reservation.data.entity.Owner;
import com.example.reservation.data.entity.Shop;
import com.example.reservation.repository.OwnerRepository;
import com.example.reservation.repository.ShopRepository;
import com.example.reservation.service.inter.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

  private final ShopRepository storeRepository;
  private final OwnerRepository ownerRepository;

  @Override
  public ShopDetailDto add(ShopAddDto storeDto, String ownerEmail) {

    validateDuplicateStore(storeDto.getShopName());
    Optional<Owner> owner = ownerRepository.findByEmail(ownerEmail);

    if (owner.isPresent()) {
      storeDto.setOwner(owner.get());
      Shop shop = storeRepository.save(ShopAddDto.toEntity(storeDto));

      return ShopDetailDto.fromEntity(shop);
    } else {
      throw new RuntimeException("이메일 정보가 유효하지 않아 매장등록에 실패했습니다.");
    }
  }

  public void validateDuplicateStore(String storeName) {
    boolean isStore = storeRepository.existsByShopName(storeName);
    if (isStore) {
      throw new RuntimeException("이미 등록된 매장입니다.");
    }
  }
}
