package com.zerobase.shopping.member.service;

import com.zerobase.shopping.exception.impl.MemberNotFound;
import com.zerobase.shopping.exception.impl.PasswordNotMatches;
import com.zerobase.shopping.member.dto.MemberResponse;
import com.zerobase.shopping.member.dto.SignInRequest;
import com.zerobase.shopping.member.dto.SignUpRequest;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.dao.AccountDao;
import com.zerobase.shopping.member.dto.MemberDto;
import com.zerobase.shopping.model.MemberModel;
import com.zerobase.shopping.member.repository.MemberRepository;
import com.zerobase.shopping.service.MailService;
import java.security.SecureRandom;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final AccountDao accountDao;
  private final MemberModel memberModel;
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public MemberDto loadUserByUsername(String username) {
    MemberEntity memberEntity = this.memberRepository.findByUsername(username)
        .orElseThrow(MemberNotFound::new);

    MemberDto dto = MemberDto.builder()
        .memberId(memberEntity.getMemberId())
        .username(memberEntity.getUsername())
        .role(memberEntity.getRole())
        .mail(memberEntity.getRole())
        .nickname(memberEntity.getNickname())
        .password(memberEntity.getPassword())
        .build();

    log.info(dto.getUsername() + "정보 로드");
    return dto;
  }

  //회원가입
  public MemberDto signup(SignUpRequest request) {

    request.setPassword((this.passwordEncoder.encode(request.getPassword())));
    MemberEntity entity = request.toEntity();
    memberRepository.save(entity);

    return MemberDto.toDto(entity);
  }

  //로그인
  @Transactional
  public MemberResponse authenticate(SignInRequest request) {

    MemberEntity user = memberRepository.findByUsername(request.getUsername())
        .orElseThrow(MemberNotFound::new);

    if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new PasswordNotMatches();
    }

    return MemberResponse.getMemberResponse(user);
  }

  //id 중복체크
  public boolean idCheck(String userId) {
    return this.accountDao.idCheck(userId);
  }

  //mail 중복체크

  public boolean mailCheck(String mail) {
    boolean result = this.accountDao.mailCheck(mail);
    return result;
  }

  //닉네임 중복체크

  public boolean nicknameCheck(String nickname) {
    boolean result = this.accountDao.nickNameCheck(nickname);
    return result;
  }

  //회원정보 조회

  public Optional<AccountDto> userDetails(String userId) {
    Optional<AccountDto> result = this.accountDao.userDetails(userId);
    return result;
  }

  //비밀번호 체크
  public boolean checkPassword(MemberModel request) {
    AccountDto user = this.accountDao.checkPassword(request.getUserId()).get();
    boolean result = (this.passwordEncoder.matches(request.getPassword(), user.getPassword()));
    return result;
  }

  //회원정보 수정
  public AccountDto updateProfile(AccountDto accountDto) {
    this.accountDao.updateProfile(accountDto);

    return accountDto;
  }

  //회원정보 삭제

  public AccountDto resign(AccountDto accountDto) {
    this.accountDao.resign(accountDto);

    return accountDto;
  }

  //메일주소로 userId찾기
  public String findId(String mail) {
    String userId = this.accountDao.findId(mail);

    return userId;
  }

  //비밀번호 변경

  public void changePassword(AccountDto accountDto) {
    log.info("비밀번호 변경");
    this.accountDao.changePassword(accountDto);
  }


  //userId, 메일주소로 비밀번호 초기화
  public String findPassword(String userId, String mail) {
    Optional<AccountDto> request = this.accountDao.findPassword(userId, mail);
    String result = "";

    if (!request.isEmpty()) {
      String tempPassword = getTempPassword();
      AccountDto requestDto = request.get();

      log.info(requestDto.getUserId());

      MemberModel model = MemberModel.builder()
          .userId(requestDto.getUserId())
          .password(this.passwordEncoder.encode(tempPassword))
          .mail(requestDto.getMail())
          .build();
      log.info("model" + model);
      AccountDto accountDto = model.toDto();
      log.info("modeltoDTO" + accountDto);
      this.changePassword(accountDto);
      mailService.findPassword(accountDto, tempPassword);
      result = "임시 비밀번호 발송 성공";
      log.info("비밀번호 변경" + userId);

    } else {
      result = "ID와 메일 주소를 확인해 주세요";
    }

    return result;
  }

  //랜덤비밀번호 로직

  public String getTempPassword() {
    char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    StringBuffer sb = new StringBuffer();
    SecureRandom sr = new SecureRandom();

    int idx = 0;
    int len = charSet.length;

    for (int i = 0; i < 10; i++) {
      idx = sr.nextInt(len);
      sb.append(charSet[idx]);
    }

    return sb.toString();
  }


}
