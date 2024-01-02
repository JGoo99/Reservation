package com.example.reservation.service.impl.owner;

import com.example.reservation.data.dto.join.OwnerJoinDto;
import com.example.reservation.data.dto.login.OwnerLoginDto;
import com.example.reservation.data.entity.Owner;
import com.example.reservation.repository.OwnerRepository;
import com.example.reservation.service.inter.owner.OwnerMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerMemberServiceImpl implements OwnerMemberService {

  private final OwnerRepository ownerRepository;
  private final PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Override
  public void join(OwnerJoinDto ownerJoinDto) {

    validateDuplicateMember(ownerJoinDto);

    ownerJoinDto.setPassword(bCryptPasswordEncoder.encode(ownerJoinDto.getPassword()));
    ownerRepository.save(OwnerJoinDto.toEntity(ownerJoinDto));
  }

  @Override
  public boolean login(OwnerLoginDto ownerLoginDto) {
    Owner owner = ownerRepository.findByEmail(ownerLoginDto.getEmail())
      .orElseThrow(() -> new RuntimeException("가입하지 않은 이메일"));

    if (bCryptPasswordEncoder
      .matches(ownerLoginDto.getPassword(), owner.getPassword())) {
      throw new RuntimeException("일치하지 않는 비밀번호");
    }

    return true;
  }

  @Override
  public void validateDuplicateMember(OwnerJoinDto ownerJoinDto) {
    boolean isOwner = ownerRepository.existsByEmail(ownerJoinDto.getEmail());
    if (isOwner) {
      throw new RuntimeException("이미 회원가입된 이메일입니다.");
    }
  }
}
