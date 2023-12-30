package com.example.reservation.service.inter.owner;

import com.example.reservation.data.dto.join.OwnerJoinDto;

public interface OwnerMemberService {

  void join(OwnerJoinDto ownerJoinDto);

  boolean login(OwnerJoinDto ownerJoinDto);

  void validateDuplicateMember(OwnerJoinDto ownerJoinDto);
}
