package com.example.reservation.repository;

import com.example.reservation.data.dto.user.UserJoinDto;
import com.example.reservation.data.dto.user.UserLoginDto;
import com.example.reservation.data.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ReviewRepository reviewRepository;

  @Spy
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void save() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();

    UserJoinDto userJoinDto = getUserJoinDto();
    userJoinDto.setPassword(bCryptPasswordEncoder.encode(userJoinDto.getPassword()));

    // when
    User user = userRepository.save(UserJoinDto.toEntity(userJoinDto));

    // then
    assertEquals(userJoinDto.getEmail(), user.getEmail());
    assertEquals(bCryptPasswordEncoder.matches("123", user.getPassword()), true);
  }

  @Test
  void findByEmail() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    UserJoinDto userJoinDto = getUserJoinDto();

    userJoinDto.setPassword(bCryptPasswordEncoder.encode(userJoinDto.getPassword()));
    User user = userRepository.save(UserJoinDto.toEntity(userJoinDto));

    // when
    User selectedUser = userRepository.findByEmail(userJoinDto.getEmail()).orElseThrow(() -> new RuntimeException(""));

    // then
    assertEquals(user.getEmail(), selectedUser.getEmail());
  }

  @Test
  void FailTest_findByEmail() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    UserJoinDto userJoinDto = getUserJoinDto();

    userJoinDto.setPassword(bCryptPasswordEncoder.encode(userJoinDto.getPassword()));
    User user = userRepository.save(UserJoinDto.toEntity(userJoinDto));

    // when
    User selectedUser = userRepository.findByEmail("ex@naver.com").orElse(null);

    // then
    assertNull(selectedUser);
  }

  @Test
  void existByEmail() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    UserJoinDto userJoinDto = getUserJoinDto();

    userJoinDto.setPassword(bCryptPasswordEncoder.encode(userJoinDto.getPassword()));
    User user = userRepository.save(UserJoinDto.toEntity(userJoinDto));

    // when
    boolean isUser = userRepository.existsByEmail(userJoinDto.getEmail());

    // then
    assertTrue(isUser);
  }

  @Test
  void FailTest_existByEmail() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    UserJoinDto userJoinDto = getUserJoinDto();

    userJoinDto.setPassword(bCryptPasswordEncoder.encode(userJoinDto.getPassword()));
    User user = userRepository.save(UserJoinDto.toEntity(userJoinDto));

    // when
    boolean isUser = userRepository.existsByEmail("ex@naver.com");

    // then
    assertFalse(isUser);
  }


  BCryptPasswordEncoder getbCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  UserJoinDto getUserJoinDto() {
    return UserJoinDto.builder()
      .email("goo@gmail.com")
      .username("goo")
      .password("123")
      .address("구의로")
      .phone("010-1234-5678")
      .build();
  }

  UserLoginDto getUserLoginDto() {
    return UserLoginDto.builder()
      .email("goo@gmail.com")
      .password("123")
      .build();
  }

  User getUser() {
    return User.builder()
      .email("goo@gmail.com")
      .username("goo")
      .password("1234")
      .address("구의로")
      .phone("010-1234-5678")
      .build();
  }

}