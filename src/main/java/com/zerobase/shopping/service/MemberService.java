package com.zerobase.shopping.service;

import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.dao.AccountDao;
import com.zerobase.shopping.model.AccountModel;
import java.security.SecureRandom;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

  private final AccountDao accountDao;
  private final AccountModel accountModel;
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public AccountModel loadUserByUsername(String userId) {
    AccountDto accountDto = this.accountDao.userDetails(userId)
        .orElseThrow(()-> new UsernameNotFoundException("유저를 찾을 수 없습니다" + " -> " + userId));

    AccountModel model = accountModel.builder()
        .no(accountDto.getNo())
        .userId(accountDto.getUserId())
        .role(accountDto.getRole())
        .mail(accountDto.getRole())
        .nickName(accountDto.getNickName())
        .password(accountDto.getPassword())
        .build();

    log.info(model.getUsername() + "정보 로드");
    return model;
  }

  //회원가입
  public AccountDto signup(AccountModel.SignUp request) {

    request.setPassword((this.passwordEncoder.encode(request.getPassword())));
    AccountDto accountDto = request.toDto();
    this.accountDao.signup(accountDto);


    return accountDto;
  }

  //로그인

  public AccountDto authenticate(AccountModel.SignIn request) {
    AccountDto user = this.accountDao.checkPassword(request.getUserId())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디 입니다"));

    if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다");
    }
    return user;
  }

  //id 중복체크
  public boolean idCheck(String userId) {
    boolean result = this.accountDao.idCheck(userId);
    return result;
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
  public boolean checkPassword(AccountModel request) {
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

      AccountModel model = AccountModel.builder()
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
    char[] charSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd' , 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
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
