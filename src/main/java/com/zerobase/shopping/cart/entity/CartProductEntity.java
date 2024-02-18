package com.zerobase.shopping.cart.entity;

import com.zerobase.shopping.product.entity.ProductEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "cartProduct")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cartProductId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cartId")
  private CartEntity cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "productId")
  private ProductEntity product;

  private int count;

 public void changeCount(int count) {
   this.count = count;
 }
}
