package com.example.reservation.service.impl.user;

import com.example.reservation.data.dto.user.UserJoinDto;
import com.example.reservation.data.dto.user.UserLoginDto;
import com.example.reservation.data.entity.User;
import com.example.reservation.repository.UserRepository;
import com.example.reservation.service.inter.user.UserMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMemberServiceImpl implements UserMemberService {

  private final UserRepository userRepository;
  private final PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Override
  public void join(UserJoinDto userJoinDto) {

    validateDuplicateMember(userJoinDto);

    userJoinDto.setPassword(bCryptPasswordEncoder.encode(userJoinDto.getPassword()));
    userRepository.save(UserJoinDto.toEntity(userJoinDto));
  }

  @Override
  public boolean login(UserLoginDto userLoginDto) {
    User user = userRepository.findByEmail(userLoginDto.getEmail())
      .orElseThrow(() -> new RuntimeException("가입하지 않은 이메일"));

    if (!bCryptPasswordEncoder
      .matches(userLoginDto.getPassword(), user.getPassword())) {
      throw new RuntimeException("일치하지 않는 비밀번호");
    }

    return true;
  }

  @Override
  public void validateDuplicateMember(UserJoinDto userJoinDto) {
    boolean isUser = userRepository.existsByEmail(userJoinDto.getEmail());
    if (isUser) {
      throw new RuntimeException("이미 회원가입된 이메일입니다.");
    }
  }
}
