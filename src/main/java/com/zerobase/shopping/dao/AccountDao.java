package com.zerobase.shopping.dao;

import com.zerobase.shopping.dto.AccountDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AccountDao {
  void signup(AccountDto accountDto);
  boolean idCheck(String userid);
  boolean mailCheck(String mail);
  boolean nicknameCheck(String nickname);
  AccountDto userDetails(String userid);
  void updateProfile(AccountDto accountDto);

  void resign(AccountDto accountDto);

}
