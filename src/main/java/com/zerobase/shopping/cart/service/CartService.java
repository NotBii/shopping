package com.zerobase.shopping.cart.service;

import com.zerobase.shopping.cart.dto.CartProduct;
import com.zerobase.shopping.cart.entity.CartEntity;
import com.zerobase.shopping.cart.entity.CartProductEntity;
import com.zerobase.shopping.cart.repository.CartProductRepository;
import com.zerobase.shopping.commons.exception.impl.MemberNotFound;
import com.zerobase.shopping.commons.exception.impl.NoResult;
import com.zerobase.shopping.commons.exception.impl.ProductNotFound;
import com.zerobase.shopping.commons.exception.impl.UserNotMatch;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.member.repository.MemberRepository;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
  private final CartProductRepository cartProductRepository;
  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;
  //TODO 탈퇴시 cart 삭제

  /**장바구니 추가
   *
   * @param productId : 상품번호
   * @param count: 수량
   * @param username: 계정명
   */
  public void add(Long productId, int count, String username) {
    CartEntity cart = getCartEntity(username);
    ProductEntity product = getProductEntity(productId);
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

    CartEntity cart = getCartEntity(username);

    return CartProduct.toDtoList(cartProductRepository.findByCart(cart));

  }
  /**
   * 장바구니 수량변경
   * @param cartProductId : 상품번호
   * @param count : 수량
   */

  public void changeProductCount(Long cartProductId, int count) {
    CartProductEntity entity = getCartProductEntity(cartProductId);
    entity.changeCount(count);
    cartProductRepository.save(entity);
  }

  public void changeProductCount(String username, Long cartProductId, int count) {
    checkMember(username, cartProductId);
    CartProductEntity entity = getCartProductEntity(cartProductId);
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
    Long requestId = memberRepository.findByUsername(username).orElseThrow(MemberNotFound::new).getCart().getCartId();
    Long cartId = getCartProductEntity(cartProductId).getCart().getCartId();
    if (!cartId.equals(requestId)) {
      throw new UserNotMatch();
    }
  }


  private CartProductEntity getCartProductEntity(Long cartProductId) {

    return cartProductRepository.findById(cartProductId).orElseThrow(NoResult::new);

  }

  private CartEntity getCartEntity(String username) {
    MemberEntity member = memberRepository.findByUsername(username).orElseThrow(MemberNotFound::new);
    log.info(member.toString());
    return member.getCart();
  }


  private ProductEntity getProductEntity(Long productId) {

    return productRepository.findByProductId(productId).orElseThrow(
        ProductNotFound::new);
  }



}