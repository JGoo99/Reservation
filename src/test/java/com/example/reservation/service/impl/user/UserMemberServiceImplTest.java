package com.example.reservation.service.impl.user;

import com.example.reservation.data.dto.user.UserJoinDto;
import com.example.reservation.data.dto.user.UserLoginDto;
import com.example.reservation.data.entity.User;
import com.example.reservation.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMemberServiceImplTest {

  @InjectMocks
  UserMemberServiceImpl userMemberService;

  @Mock
  UserRepository userRepository;

  @Spy
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void join() {
    // given
    bCryptPasswordEncoder = getbCryptPasswordEncoder();
    UserJoinDto userJoinDto = getUserJoinDto();
    userJoinDto.setPassword(bCryptPasswordEncoder.encode(userJoinDto.getPassword()));
    when(userRepository.save(any())).thenReturn(UserJoinDto.toEntity(userJoinDto));

    // when
    userMemberService.join(userJoinDto);
  }

  BCryptPasswordEncoder getbCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  UserJoinDto getUserJoinDto() {
    return UserJoinDto.builder()
      .email("goo@gmail.com")
      .username("goo")
      .password("1234")
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