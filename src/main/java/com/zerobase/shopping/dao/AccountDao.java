package com.zerobase.shopping.dao;

import com.zerobase.shopping.dto.AccountDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AccountDao {
  void signup(AccountDto accountDto);
  boolean idCheck(String userid);
  boolean mailCheck(String mail);
  boolean nicknameCheck(String nickname);
  void updateProfile(AccountDto accountDto);
  void resign(AccountDto accountDto);

  Optional<AccountDto> userDetails(String userId);

}
