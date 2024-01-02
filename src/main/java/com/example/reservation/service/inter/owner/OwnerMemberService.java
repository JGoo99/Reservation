package com.example.reservation.service.inter.owner;

import com.example.reservation.data.dto.join.OwnerJoinDto;
import com.example.reservation.data.dto.login.OwnerLoginDto;

public interface OwnerMemberService {

  void join(OwnerJoinDto ownerJoinDto);

  boolean login(OwnerLoginDto ownerLoginDto);

  void validateDuplicateMember(OwnerJoinDto ownerJoinDto);
}
