package com.example.reservation.service.impl.main;

import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopDetailDto;
import com.example.reservation.data.entity.Owner;
import com.example.reservation.data.entity.Shop;
import com.example.reservation.repository.OwnerRepository;
import com.example.reservation.repository.ShopRepository;
import com.example.reservation.service.inter.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

  private final ShopRepository shopRepository;
  private final OwnerRepository ownerRepository;

  @Override
  public ShopDetailDto add(ShopAddDto shopAddDto, String ownerEmail) {

    validateDuplicateStore(shopAddDto.getShopName());
    Owner owner = validateOwnerEmail(ownerEmail);

    shopAddDto.setOwnerId(owner.getId());
    Shop shop = shopRepository.save(ShopAddDto.toEntity(shopAddDto));

    return ShopDetailDto.fromEntity(shop);
  }

  @Override
  public List<ShopDetailDto> getShopList(Long ownerId) {
    List<Shop> shops = shopRepository.findAllByOwnerId(ownerId);

    List<ShopDetailDto> shopDetailList = new ArrayList<>();
    for (Shop s : shops) {
      shopDetailList.add(ShopDetailDto.fromEntity(s));
    }

    return shopDetailList;
  }

  @Override
  public ShopDetailDto getShopDetails(Long id) {
    Optional<Shop> shop = shopRepository.findById(id);
    if (!shop.isPresent()) {
      throw new RuntimeException("해당 매장을 찾을 수 없습니다.");
    }

    return ShopDetailDto.fromEntity(shop.get());
  }

  public Owner validateOwnerEmail(String ownerEmail) {

    Optional<Owner> owner = ownerRepository.findByEmail(ownerEmail);
    if (!owner.isPresent()) {
      throw new RuntimeException("이메일 정보가 유효하지 않아 매장등록에 실패했습니다.");
    }
    return owner.get();
  }

  public void validateDuplicateStore(String storeName) {
    boolean isStore = shopRepository.existsByShopName(storeName);
    if (isStore) {
      throw new RuntimeException("이미 등록된 매장입니다.");
    }
  }
}
