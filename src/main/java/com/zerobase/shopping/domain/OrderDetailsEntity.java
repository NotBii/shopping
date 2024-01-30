package com.zerobase.shopping.domain;

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
  private OrderEntity orderId;
  @ManyToOne
  @JoinColumn(name = "productId")
  private ProductEntity productId;

  private String name;
  private String status;
}
