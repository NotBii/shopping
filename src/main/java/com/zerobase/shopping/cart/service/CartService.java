package com.zerobase.shopping.cart.service;

import com.zerobase.shopping.cart.dto.CartProduct;
import com.zerobase.shopping.cart.entity.CartEntity;
import com.zerobase.shopping.cart.entity.CartProductEntity;
import com.zerobase.shopping.cart.repository.CartProductRepository;
import com.zerobase.shopping.commons.GetEntity;
import com.zerobase.shopping.commons.exception.impl.MemberNotFound;
import com.zerobase.shopping.commons.exception.impl.NoResult;
import com.zerobase.shopping.commons.exception.impl.ProductNotFound;
import com.zerobase.shopping.commons.exception.impl.UserNotMatch;
import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.member.repository.MemberRepository;
import com.zerobase.shopping.order.dto.ProductCount;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
  private final CartProductRepository cartProductRepository;
  private final GetEntity getEntity;
  //TODO 탈퇴시 cart 삭제

  /**장바구니 추가
   *
   * @param productId : 상품번호
   * @param count: 수량
   * @param username: 계정명
   */
  public void add(Long productId, int count, String username) {
    CartEntity cart = getEntity.cartEntity(username);
    ProductEntity product = getEntity.productEntity(productId);
    Optional<CartProductEntity> checkDuplicate = cartProductRepository.findByCartAndProduct(cart, product);

    if (checkDuplicate.isPresent()) {
      CartProductEntity cartProduct = checkDuplicate.get();
      changeProductCount(cartProduct.getCartProductId(), count);

    } else {
      CartProductEntity cartProduct = CartProductEntity.builder()
          .product(product)
          .cart(cart)
          .count(count)
          .build();
      cartProductRepository.save(cartProduct);
    }

  }

  public List<CartProduct> showAll(String username) {

    CartEntity cart = getEntity.cartEntity(username);

    return CartProduct.toDtoList(cartProductRepository.findByCart(cart));

  }
  /**
   * 장바구니 수량변경
   * @param cartProductId : 상품번호
   * @param count : 수량
   */

  public void changeProductCount(Long cartProductId, int count) {
    CartProductEntity entity = getEntity.cartProductEntity(cartProductId);
    entity.changeCount(count);
    cartProductRepository.save(entity);
  }

  public void changeProductCount(String username, Long cartProductId, int count) {
    checkMember(username, cartProductId);
    CartProductEntity entity = getEntity.cartProductEntity(cartProductId);
    if (count == 0) {
      cartProductRepository.delete(entity);
    } else {
      entity.changeCount(count);
      cartProductRepository.save(entity);
    }
  }

  /** 요청자와 카트 주인이 일치하는지 검증
   *
   * @param username : 계정명
   * @param cartProductId : 장바구니상품번호
   */
  private void checkMember(String username, Long cartProductId) {
    Long requestId = getEntity.memberEntity(username).getCart().getCartId();
    Long cartId = getEntity.cartEntity(cartProductId).getCartId();
    if (!cartId.equals(requestId)) {
      throw new UserNotMatch();
    }
  }

  @Transactional
  public ArrayList<ProductCount> toOrder(ArrayList<CartProduct> request, MemberDetails member) {
    ArrayList<ProductCount> result = new ArrayList<>();
    for (CartProduct cp : request) {
      ProductCount productCount = ProductCount.builder()
          .productId(cp.getProductId())
          .count(cp.getCount())
          .build();
      result.add(productCount);
      changeProductCount(member.getUsername(), cp.getCartProductId(), 0);
    }

    return result;
  }
}