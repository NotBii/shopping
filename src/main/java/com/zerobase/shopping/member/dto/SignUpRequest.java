package com.zerobase.shopping.member.dto;

import com.zerobase.shopping.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {
  private String username;
  private String password;
  private String nickname;
  private String mail;
  private String role;

  public MemberEntity toEntity() {
    return MemberEntity.builder()
        .username(this.username)
        .password(this.password)
        .nickname(this.nickname)
        .mail(this.mail)
        .role(this.role)
        .build();
  }
}