package com.example.reservation.repository;

import com.example.reservation.data.dto.owner.OwnerJoinDto;
import com.example.reservation.data.dto.owner.OwnerLoginDto;
import com.example.reservation.data.entity.Owner;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OwnerRepositoryTest {

  @Autowired
  OwnerRepository ownerRepository;

  @Autowired
  ReviewRepository reviewRepository;

  @Spy
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void save() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();

    OwnerJoinDto ownerJoinDto = getOwnerJoinDto();
    ownerJoinDto.setPassword(bCryptPasswordEncoder.encode(ownerJoinDto.getPassword()));

    // when
    Owner owner = ownerRepository.save(OwnerJoinDto.toEntity(ownerJoinDto));

    // then
    assertEquals(ownerJoinDto.getEmail(), owner.getEmail());
    assertEquals(bCryptPasswordEncoder.matches("123", owner.getPassword()), true);
  }

  @Test
  void findByEmail() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    OwnerJoinDto ownerJoinDto = getOwnerJoinDto();

    ownerJoinDto.setPassword(bCryptPasswordEncoder.encode(ownerJoinDto.getPassword()));
    Owner owner = ownerRepository.save(OwnerJoinDto.toEntity(ownerJoinDto));

    // when
    Owner selelctedOwner = ownerRepository.findByEmail(ownerJoinDto.getEmail()).orElseThrow(() -> new RuntimeException(""));

    // then
    assertEquals(owner.getEmail(), selelctedOwner.getEmail());
  }

  @Test
  void FailTest_findByEmail() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    OwnerJoinDto ownerJoinDto = getOwnerJoinDto();

    ownerJoinDto.setPassword(bCryptPasswordEncoder.encode(ownerJoinDto.getPassword()));
    Owner owner = ownerRepository.save(OwnerJoinDto.toEntity(ownerJoinDto));

    // when
    Owner selelctedOwner = ownerRepository.findByEmail("ex@naver.com").orElse(null);

    // then
    assertNull(selelctedOwner);
  }

  @Test
  void existByEmail() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    OwnerJoinDto ownerJoinDto = getOwnerJoinDto();

    ownerJoinDto.setPassword(bCryptPasswordEncoder.encode(ownerJoinDto.getPassword()));
    Owner owner = ownerRepository.save(OwnerJoinDto.toEntity(ownerJoinDto));

    // when
    boolean isOwner = ownerRepository.existsByEmail(ownerJoinDto.getEmail());

    // then
    assertTrue(isOwner);
  }

  @Test
  void FailTest_existByEmail() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    OwnerJoinDto ownerJoinDto = getOwnerJoinDto();

    ownerJoinDto.setPassword(bCryptPasswordEncoder.encode(ownerJoinDto.getPassword()));
    Owner owner = ownerRepository.save(OwnerJoinDto.toEntity(ownerJoinDto));

    // when
    boolean isOwner = ownerRepository.existsByEmail("ex@naver.com");

    // then
    assertFalse(isOwner);
  }


  BCryptPasswordEncoder getbCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  OwnerJoinDto getOwnerJoinDto() {
    return OwnerJoinDto.builder()
      .email("owner@gmail.com")
      .busNumber("111-11-11111")
      .ownerName("goo")
      .password("123")
      .phone("010-1234-5678")
      .build();
  }

  OwnerLoginDto getOwnerLoginDto() {
    return OwnerLoginDto.builder()
      .email("owner@gmail.com")
      .password("123")
      .build();
  }

  Owner getOwner() {
    return Owner.builder()
      .email("goo@gmail.com")
      .busNumber("111-11-11111")
      .ownerName("goo")
      .password("1234")
      .phone("010-1234-5678")
      .build();
  }

}