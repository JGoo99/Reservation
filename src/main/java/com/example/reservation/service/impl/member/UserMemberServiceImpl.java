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

import java.time.LocalDateTime;
import java.util.List;

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

  /**
   * 유저 개인정보 변환 메소드
   * - 예약 db 저장 시 유저 id, 예약자명(유저명), 예약자번호(유저번호) 를 따로 저장했음.
   * - 별도로 변경하는 로직이 필요
   * @param editDto .
   * @param userId .
   * @return 변경된 데이터를 반환
   */
  @Override
  public UserJoinDto edit(UserJoinDto editDto, Long userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("해당 유저 정보가 없습니다."));

    // 이메일 변경 시 중복확인 후 변경 [이메일 : unique = true]
    if (!user.getEmail().equals(editDto.getEmail())) {
      validateDuplicateMember(editDto);
      user.setEmail(editDto.getEmail());
    }
    user.setUsername(editDto.getUsername());
    user.setNickname(editDto.getNickname());
    user.setPhone(editDto.getPhone());
    user.setAddress(editDto.getAddress());

    // 예약 DB 에서 예약자 이름과 번호를 수정한다. (현재시간 이후의 예약 데이터만 변경)
    if (reservationRepository.existsByUserId(userId)) {
      List<Reservation> reservation =
        reservationRepository.findAllByReservedAtAfterAndUserId(LocalDateTime.now(), userId);

      String username = editDto.getUsername();
      String phone = editDto.getPhone();
      for (int i = 0; i < reservation.size(); i++) {
        Reservation cur = reservation.get(i);

        cur.setUserName(username);
        cur.setUserPhone(phone);
        reservationRepository.save(cur);
      }
    }

    return UserJoinDto.from(userRepository.save(user));
  }
}
