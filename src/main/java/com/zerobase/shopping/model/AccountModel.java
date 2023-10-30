package com.zerobase.shopping.model;

import com.zerobase.shopping.dto.AccountDto;
import lombok.Data;

@Data
public class AccountModel {
  private int no;
  private String userid;
  private String password;
  private String nickname;
  private String mail;
  private String role;
  private String date;

  @Data
  public static class SignIn {
    private String userid;
    private String password;
  }

  @Data
  public static class SignUp {
    private String userid;
    private String password;
    private String nickname;
    private String mail;
    private String role;

    public AccountDto toDto() {
      return AccountDto.builder()
          .userid(this.userid)
          .password(this.password)
          .nickname(this.nickname)
          .mail(this.mail)
          .role(this.role)
          .build();
    }
  }

}
