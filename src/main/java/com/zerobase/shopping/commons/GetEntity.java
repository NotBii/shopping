package com.zerobase.shopping.commons;

import com.zerobase.shopping.cart.entity.CartEntity;
import com.zerobase.shopping.cart.entity.CartProductEntity;
import com.zerobase.shopping.cart.repository.CartProductRepository;
import com.zerobase.shopping.cart.repository.CartRepository;
import com.zerobase.shopping.commons.exception.impl.CartNotFound;
import com.zerobase.shopping.commons.exception.impl.MemberNotFound;
import com.zerobase.shopping.commons.exception.impl.NoArticle;
import com.zerobase.shopping.commons.exception.impl.NoResult;
import com.zerobase.shopping.commons.exception.impl.OrderNotFound;
import com.zerobase.shopping.img.repository.ImgRepository;
import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.inquiry.repository.InquiryRepository;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.member.repository.MemberRepository;
import com.zerobase.shopping.order.entity.OrderDetailsEntity;
import com.zerobase.shopping.order.entity.OrderEntity;
import com.zerobase.shopping.order.repository.OrderDetailsRepository;
import com.zerobase.shopping.order.repository.OrderRepository;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.product.repository.ProductRepository;
import com.zerobase.shopping.review.entity.RecommendEntity;
import com.zerobase.shopping.review.entity.ReviewEntity;
import com.zerobase.shopping.review.repository.RecommendRepository;
import com.zerobase.shopping.review.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class GetEntity {

  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  private final OrderDetailsRepository orderDetailsRepository;
  private final ReviewRepository reviewRepository;
  private final CartRepository cartRepository;
  private final CartProductRepository cartProductRepository;
  private final InquiryRepository inquiryRepository;
  private final ImgRepository imgRepository;
  private final RecommendRepository recommendRepository;

  public MemberEntity memberEntity(String username) {
    return memberRepository.findByUsername(username).orElseThrow(MemberNotFound::new);
  }

  public InquiryEntity inquiryEntity(long id) {
    return inquiryRepository.findById(id).orElseThrow(NoArticle::new);
  }

  public ProductEntity productEntity(long productId) {
    return productRepository.findByProductId(productId).orElseThrow(NoResult::new);
  }

  public ReviewEntity reviewEntity(long reviewId, int isDeleted) {
    return reviewRepository.findByReviewIdAndIsDeleted(reviewId, isDeleted)
        .orElseThrow(NoArticle::new);
  }

  public Optional<ReviewEntity> reviewEntity(MemberEntity member, ProductEntity product, int isDelted) {
    return reviewRepository.findByWriterAndProductAndIsDeleted(member, product, isDelted);
  }

  public Optional<RecommendEntity> recommendEntity(MemberEntity memberEntity) {

    return recommendRepository.findByMember(memberEntity);
  }

  public OrderEntity orderEntity(Long orderId) {
    return orderRepository.findByOrderId(orderId).orElseThrow(OrderNotFound::new);
  }

  public List<OrderDetailsEntity> orderDetailsEntity(OrderEntity entity) {
    return orderDetailsRepository.findByOrder(entity);
  }

  public boolean isExistOrderDetails(MemberEntity member, ProductEntity product) {
    return orderDetailsRepository.findByMemberAndProduct(member, product);
  }
  public CartEntity cartEntity(String username) {
    MemberEntity member = memberEntity(username);
    return member.getCart();
  }

  public CartEntity cartEntity(Long cartProductId) {

    return cartProductEntity(cartProductId).getCart();
  }
  public CartProductEntity cartProductEntity(Long cartProductId) {
    return cartProductRepository.findById(cartProductId).orElseThrow(CartNotFound::new);
  }

}
