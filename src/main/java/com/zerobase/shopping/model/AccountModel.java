package com.zerobase.shopping.model;

import com.zerobase.shopping.dto.AccountDto;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component

public class AccountModel implements UserDetails {

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


      authorities.add(new SimpleGrantedAuthority(this.getRole()));


    return authorities;
  }

  @Override
  public String getUsername() {
    return this.getUserid();
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
