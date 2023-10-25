package com.zerobase.shopping.mapper;

import com.zerobase.shopping.vo.AccountVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AccountMapper {
  void signup(AccountVo accountVo);
  boolean idCheck(String userid);
  boolean mailCheck(String mail);
  AccountVo userDetails(String userid);

  void updateProfile(AccountVo accountVo);

}
