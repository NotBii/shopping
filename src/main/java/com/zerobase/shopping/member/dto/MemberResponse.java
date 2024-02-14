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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class MemberResponse {

  private String username;
  private String nickname;
  private String mail;
  private String role;

  private MemberResponse(MemberEntity entity) {
    this.username = entity.getUsername();
    this.nickname = entity.getNickname();
    this.mail = entity.getMail();
    this.role = entity.getRole();
  }

  public static MemberResponse getMemberResponse(MemberEntity entity) {
    return new MemberResponse(entity);
  }
}


