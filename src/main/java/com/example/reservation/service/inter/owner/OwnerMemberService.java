package com.example.reservation.service.inter.owner;

import com.example.reservation.data.dto.owner.OwnerJoinDto;
import com.example.reservation.data.dto.owner.OwnerLoginDto;

public interface OwnerMemberService {

  void join(OwnerJoinDto ownerJoinDto);

  boolean login(OwnerLoginDto ownerLoginDto);

  void validateDuplicateMember(OwnerJoinDto ownerJoinDto);
}
