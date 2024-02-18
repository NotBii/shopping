package com.zerobase.shopping.order.entity;

import com.zerobase.shopping.commons.exception.impl.CodeNotFit;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.order.service.ServiceCodeConverter.statusConverter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "orders")
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
  private String payCheck;
  private String title;
  private String status;
  private String recipient;
  private long cost;
  @CreatedDate
  private LocalDateTime createdDate;


  public void changePayCheck(String request) {
    this.status = request;
  }

  public void changeStatus(String request) {
    this.status = request;
  }

  @PrePersist
  public void onPrePersist() {
    this.createdDate = LocalDateTime.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
  }


}
