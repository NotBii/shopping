package com.zerobase.shopping.member.entity;

import com.zerobase.shopping.cart.entity.CartEntity;
import com.zerobase.shopping.member.dto.MemberDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
  @CreatedDate
  private LocalDateTime createdDate;

  @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "cartId")
  private CartEntity cart;

  public void changePw(String pw) {
    this.password = pw;
  }

  public void updateProfile(MemberDetails memberDetails) {
    this.nickname = memberDetails.getNickname();
    this.mail = memberDetails.getMail();
  }
  @PrePersist
  public void onPrepersist() {
    this.createdDate = LocalDateTime.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
  }


}
