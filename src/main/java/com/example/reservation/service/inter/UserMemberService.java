package com.example.reservation.service.inter;

import com.example.reservation.data.dto.user.UserJoinDto;
import com.example.reservation.data.dto.user.UserLoginDto;

public interface UserMemberService {

  void join(UserJoinDto userJoinDto);

  boolean login(UserLoginDto userLoginDto);

  void validateDuplicateMember(UserJoinDto userJoinDto);

  UserJoinDto getUserInfo(Long userId);

  UserJoinDto edit(UserJoinDto editDto, Long userId);
}
