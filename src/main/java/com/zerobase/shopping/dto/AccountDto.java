package com.zerobase.shopping.dto;

import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class AccountDto implements UserDetails {
  private int no;
  private String userid;
  private String password;
  private String nickname;
  private String mail;
  private String role;
  private String date;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();

    for (String role : role.split(",")) {
      authorities.add(new SimpleGrantedAuthority(role));
    }

    return authorities;
  }

  @Override
  public String getUsername() {
    return null;
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
}
