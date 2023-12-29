package com.example.reservation.service.inter.user;

import com.example.reservation.data.dto.join.UserJoinDto;
import com.example.reservation.data.dto.login.UserLoginDto;

public interface UserMemberService {

  void join(UserJoinDto userJoinDto);

  void login(UserLoginDto userLoginDto);

  void validateDuplicateMember(UserJoinDto userJoinDto);
}
