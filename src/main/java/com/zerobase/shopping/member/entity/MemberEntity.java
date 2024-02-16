package com.zerobase.shopping.member.entity;

import com.zerobase.shopping.member.dto.MemberDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long memberId;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false, unique = true)
  private String nickname;
  @Column(nullable = false, unique = true)
  private String mail;
  @Column(nullable = false)
  private String role;
  @CreationTimestamp
  private LocalDateTime regDate;

  public void changePw(String pw) {
    this.password = pw;
  }

  public void updateProfile(MemberDetails memberDetails) {
    this.nickname = memberDetails.getNickname();
    this.mail = memberDetails.getMail();
  }

}
