package com.zerobase.shopping.order.service;

import com.zerobase.shopping.commons.exception.impl.MemberNotFound;
import com.zerobase.shopping.commons.exception.impl.NoResult;
import com.zerobase.shopping.commons.exception.impl.OrderIsProcessing;
import com.zerobase.shopping.commons.exception.impl.OrderNotFound;
import com.zerobase.shopping.commons.exception.impl.OutOfStock;
import com.zerobase.shopping.commons.exception.impl.ProductNotFound;
import com.zerobase.shopping.commons.exception.impl.UserNotMatch;
import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.order.dto.ProductCount;
import com.zerobase.shopping.order.dto.StatusRequest;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.member.repository.MemberRepository;
import com.zerobase.shopping.order.dto.OrderDetails;
import com.zerobase.shopping.order.dto.OrderListRequest;
import com.zerobase.shopping.order.dto.OrderRequest;
import com.zerobase.shopping.order.dto.OrderResponse;
import com.zerobase.shopping.order.dto.PayCheck;
import com.zerobase.shopping.order.dto.UpdateDetailsRequest;
import com.zerobase.shopping.order.entity.OrderDetailsEntity;
import com.zerobase.shopping.order.entity.OrderEntity;
import com.zerobase.shopping.order.repository.OrderDetailsRepository;
import com.zerobase.shopping.order.repository.OrderRepository;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderEntityConverter orderEntityConverter;
  private final ProductRepository productRepository;
  private final OrderDetailsRepository orderDetailsRepository;
  private final MemberRepository memberRepository;

  /**
   * 주문추가하기
   *
   * @param request  OrderRequest 객체
   * @param username 로그인유저명
   * @return
   */
  @Transactional
  public Long addOrder(OrderRequest request, String username) {
    request.setUsername(username);

    ArrayList<ProductCount> pcList = request.getProduct();
    String productName = getProductEntity(pcList.get(0).getProductId()).getTitle();

    request.setTitle(productName + " 외" + (pcList.size() - 1) + " 건");

    OrderEntity order = orderEntityConverter.toEntity(request);
    orderRepository.save(order);
    makeOrderDetails(order, request.getProduct());

    return order.getOrderId();

  }

  /**
   * @param order   주문Entity
   * @param pcList  상품id, 수량
   */
  public void makeOrderDetails(OrderEntity order, ArrayList<ProductCount> pcList) {
    for (ProductCount pc : pcList) {
      ProductEntity productEntity = getProductEntity(pc.getProductId());
      int count = pc.getCount();

      if (productEntity.getStock() < count) {
        throw new OutOfStock();
      }

      OrderDetailsEntity entity = OrderDetailsEntity.builder()
          .orderId(order)
          .productId(productEntity)
          .status("결제대기")
          .count(count)
          .cost(count * productEntity.getPrice())
          .build();

      orderDetailsRepository.save(entity);
    }
  }

  /**
   * 주문목록보기
   */
  public Page<OrderResponse> orderList(OrderListRequest request, MemberDetails member) {
    Pageable pageable = PageRequest.of(request.getPage(), 20, Sort.by("createAt").descending());
    String username = member.getUsername();
    MemberEntity entity = getMemberEntity(username);

    return orderRepository.findByMemberId(pageable, entity).map(orderEntityConverter::toResponse);

  }

  /**
   * 주문상세보기
   */
  public List<OrderDetails> orderDetails(Long orderId, MemberDetails member) {
    checkUser(orderId, member);
    OrderEntity order = getOrderEntity(orderId);

    return orderDetailsRepository.findByOrderId(order).stream()
        .map(orderEntityConverter::toOrderDetails).toList();
  }

  /**
   * 결제확인변경
   */
  public void payCheck(PayCheck request) {
    OrderEntity entity = getOrderEntity(request.getOrderId());
    entity.changePayCheck(request.getPayCheck());
  }

  /**
   * 주문상태변경
   */
  public void updateOrder(StatusRequest request) {
    OrderEntity entity = getOrderEntity(request.getOrderId());
    entity.changeStatus(request.getStatus());
  }
  /**
   * 주문상세정보 상태변경
   */
  @Transactional
  public void updateDetails(List<UpdateDetailsRequest> requests) {
    for (UpdateDetailsRequest request : requests) {
      OrderDetailsEntity entity = orderDetailsRepository.findByOrderDetailsId(request.getOrderDetailsId())
          .orElseThrow(OrderNotFound::new);
      entity.changeStatus(request.getStatus());
      orderDetailsRepository.save(entity);
    }
  }

  /**
   * 주문취소
   */
  public void cancel(Long orderId, MemberDetails member) {
    OrderEntity order = getOrderEntity(orderId);
    checkUser(orderId, member);

    if (order.getStatus().equals("상품준비중")) {
      throw new OrderIsProcessing();
    }

    order.changeStatus("주문취소");
  }



  /**
   * 요청자 확인 주문번호의 계정명과 로그인유저의 계정명이 일치하지 않고 권한이 운영자가 아닐 때 예외 발생
   *
   * @param orderId
   * @param member
   */
  private void checkUser(Long orderId, MemberDetails member) {
    OrderEntity order = getOrderEntity(orderId);

    if (!member.getUsername().equals(order.getMemberId().getUsername())
        && !member.getRole().equals("ROLE_MANGER")) {
      throw new UserNotMatch();
    }

  }

  private OrderEntity getOrderEntity(Long orderId) {
    return orderRepository.findByOrderId(orderId).orElseThrow(OrderNotFound::new);
  }

  private ProductEntity getProductEntity(Long productId) {
    return productRepository.findByProductId(productId).orElseThrow(NoResult::new);
  }

  private MemberEntity getMemberEntity(String username) {
    return memberRepository.findByUsername(username).orElseThrow(MemberNotFound::new);
  }
}
