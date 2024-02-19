package com.zerobase.shopping.order.entity;

import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
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
@Table(name = "orderDetails")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long orderDetailsId;
  @ManyToOne
  @JoinColumn(name = "orderId")
  private OrderEntity order;
  @ManyToOne
  @JoinColumn(name = "memberId")
  private MemberEntity member;
  @ManyToOne
  @JoinColumn(name = "productId")
  private ProductEntity product;
  private int count;
  private Long cost;
  private String status;
  @CreatedDate
  private LocalDateTime createdDate;

  @PrePersist
  public void onPrepersist() {
    this.createdDate = LocalDateTime.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
  }

  public void changeStatus(String text) {
    this.status = text;
  }

}
