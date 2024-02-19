package com.zerobase.shopping.order.service;

import com.zerobase.shopping.commons.GetEntity;
import com.zerobase.shopping.commons.exception.impl.OrderIsProcessing;
import com.zerobase.shopping.commons.exception.impl.OrderNotFound;
import com.zerobase.shopping.commons.exception.impl.OutOfStock;
import com.zerobase.shopping.commons.exception.impl.UserNotMatch;
import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.order.dto.ProductCount;
import com.zerobase.shopping.order.dto.StatusRequest;
import com.zerobase.shopping.member.entity.MemberEntity;
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
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  private final OrderDetailsRepository orderDetailsRepository;
  private final GetEntity getEntity;

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
    String productName = getEntity.productEntity(pcList.get(0).getProductId()).getTitle();

    request.setTitle(productName + " 외" + (pcList.size() - 1) + " 건");

    OrderEntity order = orderEntityConverter.toEntity(request);
    orderRepository.save(order);
    makeOrderDetails(order, request.getProduct());

    return order.getOrderId();

  }

  /**
   * @param order  주문Entity
   * @param pcList 상품id, 수량
   */
  public void makeOrderDetails(OrderEntity order, ArrayList<ProductCount> pcList) {
    for (ProductCount pc : pcList) {
      ProductEntity productEntity = getEntity.productEntity(pc.getProductId());
      int count = pc.getCount();

      if (productEntity.getStock() < count) {
        throw new OutOfStock();
      }

      OrderDetailsEntity entity = OrderDetailsEntity.builder()
          .order(order)
          .product(productEntity)
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
    MemberEntity entity = getEntity.memberEntity(username);

    return orderRepository.findByMember(pageable, entity).map(orderEntityConverter::toResponse);

  }

  /**
   * 주문상세보기
   */
  public List<OrderDetails> orderDetails(Long orderId, MemberDetails member) {
    checkUser(orderId, member);
    OrderEntity order = getEntity.orderEntity(orderId);

    return orderDetailsRepository.findByOrder(order).stream()
        .map(orderEntityConverter::toOrderDetails).toList();
  }

  /**
   * 결제확인변경
   */
  public void payCheck(PayCheck request) {
    OrderEntity entity = getEntity.orderEntity(request.getOrderId());
    entity.changePayCheck(request.getPayCheck());

  }

  /**
   * 주문상태변경
   */
  public void updateOrder(StatusRequest request) {
    OrderEntity entity = getEntity.orderEntity(request.getOrderId());
    entity.changeStatus(request.getStatus());
    orderRepository.save(entity);
    List<OrderDetailsEntity> detailsEntities = getEntity.orderDetailsEntity(entity);
    int isRefund = -1;
    if (request.getStatus().equals("배송중")) {
      isRefund = -1;
    } else if (request.getStatus().equals("환불완료")) {
      isRefund = 1;
    }
    for (OrderDetailsEntity de : detailsEntities) {
      de.getProduct().updateStock(de.getCount() * isRefund);
      de.changeStatus(request.getStatus());
      orderDetailsRepository.save(de);
      log.info(de.getOrderDetailsId() +" " + request.getStatus() + "처리완료");
      log.info(de.getProduct().getProductId() + " 수량 " + de.getProduct().getStock() + " 남음");
    }

  }

  /**
   * 주문상세정보 상태변경
   */
  @Transactional
  public void updateDetails(List<UpdateDetailsRequest> requests) {
    for (UpdateDetailsRequest request : requests) {
      OrderDetailsEntity entity = orderDetailsRepository.findByOrderDetailsId(
              request.getOrderDetailsId())
          .orElseThrow(OrderNotFound::new);
      entity.changeStatus(request.getStatus());
      orderDetailsRepository.save(entity);
    }
  }

  /**
   * 주문취소
   */
  public void cancel(Long orderId, MemberDetails member) {
    OrderEntity order = getEntity.orderEntity(orderId);
    checkUser(orderId, member);

    if (order.getStatus().equals("배송중")) {
      throw new OrderIsProcessing();
    }
    List<OrderDetailsEntity> detailsEntities = getEntity.orderDetailsEntity(order);
    for (OrderDetailsEntity de : detailsEntities) {
      de.changeStatus("주문취소");
      orderDetailsRepository.save(de);
      log.info(de.getOrderDetailsId() + "주문취소 처리완료");
    }

    order.changeStatus("주문취소");
    orderRepository.save(order);
    log.info(order.getOrderId() + "주문취소 완료");

  }


  /**
   * 요청자 확인 주문번호의 계정명과 로그인유저의 계정명이 일치하지 않고 권한이 운영자가 아닐 때 예외 발생
   *
   * @param orderId
   * @param member
   */
  private void checkUser(Long orderId, MemberDetails member) {
    OrderEntity order = getEntity.orderEntity(orderId);

    if (!member.getUsername().equals(order.getMember().getUsername())
        && !member.getRole().equals("ROLE_MANGER")) {
      throw new UserNotMatch();
    }

  }
}






