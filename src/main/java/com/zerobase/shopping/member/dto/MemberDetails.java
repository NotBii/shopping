package com.zerobase.shopping.member.dto;

import com.zerobase.shopping.member.entity.MemberEntity;
import java.time.LocalDateTime;
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
public class MemberDetails implements UserDetails {

  private long memberId;
  private String username;
  private String password;
  private String nickname;
  private String mail;
  private String role;
  private LocalDateTime regDate;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();


    authorities.add(new SimpleGrantedAuthority(this.getRole()));


    return authorities;
  }

  @Override
  public String getUsername() {return username;}
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
  @Override
  public String getPassword() {return null;}



  public static MemberDetails toDto(MemberEntity entity) {
    return MemberDetails.builder()
        .username(entity.getUsername())
        .nickname(entity.getNickname())
        .mail(entity.getMail())
        .role(entity.getRole())
        .regDate(entity.getRegDate())
        .build();
  }

}
