package com.zerobase.shopping.dao;


import com.zerobase.shopping.dto.AccountDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AccountDao {
  void signup(AccountDto accountDto);
  boolean idCheck(String userId);
  boolean mailCheck(String mail);
  boolean nickNameCheck(String nickName);
  void updateProfile(AccountDto accountDto);
  void resign(AccountDto accountDto);

  Optional<AccountDto> userDetails(String userId);
  Optional<AccountDto> checkPassword(String userId);

  String findId(String mail);

}
