package com.zerobase.shopping.service;

import com.zerobase.shopping.mapper.AccountMapper;
import com.zerobase.shopping.vo.AccountVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountMapper accountMapper;

  //회원가입
  public AccountVo signup(AccountVo accountVo) {

    accountMapper.signup(accountVo);

    return accountVo;
  }

  //id 중복체크
  public boolean idCheck(String userid) {
    boolean result = accountMapper.idCheck(userid);
    return result;
  }

  //mail 중복체크

  public boolean mailCheck(String mail) {
    boolean result = accountMapper.mailCheck(mail);
    return result;
  }

  //회원정보 조회

  public AccountVo userDetails(String userid) {
    AccountVo result = accountMapper.userDetails(userid);

    return result;
  }

  public AccountVo updateProfile(AccountVo accountVo) {
    accountMapper.updateProfile(accountVo);

    return accountVo;
  }

}
