package com.zerobase.shopping.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class AccountDto {
  private int no;
  private String userid;
  private String password;
  private String nickname;
  private String mail;
  private String role;
  private String date;


}
