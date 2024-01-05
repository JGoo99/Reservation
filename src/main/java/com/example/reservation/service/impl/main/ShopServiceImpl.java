package com.example.reservation.service.impl.main;

import com.example.reservation.data.dto.SearchDto;
import com.example.reservation.data.dto.shop.ShopAddDto;
import com.example.reservation.data.dto.shop.ShopInfoDto;
import com.example.reservation.data.entity.Owner;
import com.example.reservation.data.entity.Shop;
import com.example.reservation.repository.OwnerRepository;
import com.example.reservation.repository.ReviewRepository;
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
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

  private final ShopRepository shopRepository;
  private final OwnerRepository ownerRepository;

  @Override
  public ShopInfoDto add(ShopAddDto shopAddDto, String ownerEmail) {

    validateDuplicateStore(shopAddDto.getShopName());
    Owner owner = validateOwnerEmail(ownerEmail);

    shopAddDto.setOwnerId(owner.getId());
    Shop shop = shopRepository.save(ShopAddDto.toEntity(shopAddDto));

    return ShopInfoDto.fromEntity(shop);
  }

  @Override
  public List<ShopInfoDto> getOwnerShopList(Long ownerId) {
    List<Shop> shops = shopRepository.findAllByOwnerId(ownerId);

    List<ShopInfoDto> shopInfoList = new ArrayList<>();
    for (Shop s : shops) {
      shopInfoList.add(ShopInfoDto.fromEntity(s));
    }

    return shopInfoList;
  }

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

  @Override
  public boolean delete(Long shopId) {
    shopRepository.deleteById(shopId);
    // reviewRepository.deleteByShopId(shopId);

    if(shopRepository.existsById(shopId)) {
      throw new RuntimeException("매장정보가 유효하지 않아 매장 삭제에 실패했습니다.");
    }
    return true;
  }

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

  public PageRequest getPaging(SearchDto searchDto) {
    if (searchDto.getDirection() == 0) {
      return PageRequest.of(searchDto.getPageNum() - 1, searchDto.getSize(),
        Sort.by(ASC, searchDto.getDirectionColumn()));
    }
    return PageRequest.of(searchDto.getPageNum() - 1, searchDto.getSize(),
      Sort.by(DESC, searchDto.getDirectionColumn()));
  }
}
