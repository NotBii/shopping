package com.zerobase.shopping.commons.security;

import com.zerobase.shopping.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {
  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
  private static final String KEY_ROLES  = "role";
  private final MemberService memberService;

  @Value("{spring.jwt.secret}")
  private String secretKey;

  /**
   * 토큰생성
   * @param username
   * @param role
   * @return
   */

  public String generateToken(String username, String role) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put(KEY_ROLES, role);

    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, this.secretKey)
        .compact();
  }

  public String getUsername(String token) {
    return this.parseClaims(token).getSubject();
  }

  public String getKeyRoles(String token) {
    return (String) this.parseClaims(token).get("role");
  }

  //토큰유효성검증
  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) return false;

    Claims claims = this.parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  private Claims parseClaims(String token) {
    try {

      return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();

    } catch (ExpiredJwtException e) {

      return e.getClaims();

    }
  }

  public Authentication getAuthentication(String jwt) {
    UserDetails userDetails = this.memberService.loadUserByUsername(this.getUsername(jwt));

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }


}
