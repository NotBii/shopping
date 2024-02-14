package com.zerobase.shopping.domain;

import com.zerobase.shopping.member.entity.MemberEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long orderId;
  @ManyToOne
  @JoinColumn(name = "memberId")
  private MemberEntity memberId;

  private String address;
  private String phone;
  private int payCheck;
  private String status;
  private String recipient;
  private long cost;



}
