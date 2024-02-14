package com.zerobase.shopping.member.dto;

import com.zerobase.shopping.member.entity.MemberEntity;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto implements UserDetails {

  private long memberId;
  private String username;
  private String password;
  private String nickname;
  private String mail;
  private String role;
  private String date;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();


    authorities.add(new SimpleGrantedAuthority(this.getRole()));


    return authorities;
  }

  @Override
  public String getUsername() {
    return this.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }



  public static MemberDto toDto(MemberEntity entity) {
    return MemberDto.builder()
        .username(entity.getUsername())
        .password(entity.getPassword())
        .nickname(entity.getNickname())
        .mail(entity.getMail())
        .role(entity.getRole())
        .date(entity.getDate())
        .build();
  }

}
