package com.zerobase.shopping.cart.dto;

import com.zerobase.shopping.cart.entity.CartProductEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartProduct {
  private Long CartProductId;
  private Long productId;
  private int count;

  public static CartProduct toDto(CartProductEntity entity) {
    return CartProduct.builder()
        .CartProductId(entity.getCartProductId())
        .productId(entity.getProduct().getProductId())
        .count(entity.getCount())
        .build();
  }

  public static List<CartProduct> toDtoList(List<CartProductEntity> entities) {
    List<CartProduct> result = new ArrayList<>();

    for (CartProductEntity entity : entities) {
      result.add(toDto(entity));
    }

    return result;
  }
}
