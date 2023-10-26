package com.zerobase.shopping.service;

import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.dao.AccountDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountDao accountDao;

  //회원가입
  public AccountDto signup(AccountDto accountDto) {

    accountDao.signup(accountDto);

    return accountDto;
  }

  //id 중복체크
  public boolean idCheck(String userid) {
    boolean result = accountDao.idCheck(userid);
    return result;
  }

  //mail 중복체크

  public boolean mailCheck(String mail) {
    boolean result = accountDao.mailCheck(mail);
    return result;
  }

  //닉네임 중복체크

  public boolean nicknameCheck(String nickname) {
    boolean result = accountDao.nicknameCheck(nickname);
    return result;
  }

  //회원정보 조회

  public AccountDto userDetails(String userid) {
    AccountDto result = accountDao.userDetails(userid);

    return result;
  }
  //회원정보 수정
  public AccountDto updateProfile(AccountDto accountDto) {
    accountDao.updateProfile(accountDto);

    return accountDto;
  }

  //회원정보 삭제

  public AccountDto resign(AccountDto accountDto) {
    accountDao.resign(accountDto);

    return accountDto;
  }
}
