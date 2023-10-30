package com.zerobase.shopping.service;

import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.dao.AccountDao;
import com.zerobase.shopping.model.AccountModel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService{

  private final AccountDao accountDao;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String userId) {
    return this.accountDao.userDetails(userId)
        .orElseThrow(()-> new UsernameNotFoundException("유저를 찾을 수 없습니다" + " -> " + userId));

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
    AccountDto user = this.accountDao.userDetails(request.getUserid())
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
    boolean result = this.accountDao.nicknameCheck(nickname);
    return result;
  }

  //회원정보 조회

  public Optional<AccountDto> userDetails(String userId) {
    Optional<AccountDto> result = this.accountDao.userDetails(userId);

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


}