package com.example.reservation.service.impl.member;

import com.example.reservation.data.dto.user.UserJoinDto;
import com.example.reservation.data.dto.user.UserLoginDto;
import com.example.reservation.data.entity.Reservation;
import com.example.reservation.data.entity.User;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.UserRepository;
import com.example.reservation.service.inter.UserMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMemberServiceImpl implements UserMemberService {

  private final UserRepository userRepository;
  private final ReservationRepository reservationRepository;
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

  @Override
  public UserJoinDto getUserInfo(Long userId) {

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("해당 유저 정보가 없습니다."));

    return UserJoinDto.from(user);
  }

  @Override
  public UserJoinDto edit(UserJoinDto editDto, Long userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("해당 유저 정보가 없습니다."));

    validateDuplicateMember(editDto);

    user.setEmail(editDto.getEmail());
    user.setUsername(editDto.getUsername());
    user.setNickname(editDto.getNickname());
    user.setPhone(editDto.getPhone());
    user.setAddress(editDto.getAddress());

    Reservation reservation = reservationRepository.findByUserId(userId)
      .orElseThrow(() -> new RuntimeException("해당 유저 정보가 없습니다."));

    reservation.setUserName(editDto.getUsername());
    reservation.setUserPhone(editDto.getPhone());

    user = userRepository.save(user);
    reservationRepository.save(reservation);

    return UserJoinDto.from(user);
  }
}
