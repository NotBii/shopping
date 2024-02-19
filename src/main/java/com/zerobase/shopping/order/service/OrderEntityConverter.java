package com.zerobase.shopping.order.service;

import com.zerobase.shopping.member.service.MemberService;
import com.zerobase.shopping.order.dto.OrderDetails;
import com.zerobase.shopping.order.dto.OrderRequest;
import com.zerobase.shopping.order.dto.OrderResponse;
import com.zerobase.shopping.order.entity.OrderDetailsEntity;
import com.zerobase.shopping.order.entity.OrderEntity;
import com.zerobase.shopping.product.service.ProductEntityConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderEntityConverter {
  private final MemberService memberService;
  private final ProductEntityConverter productEntityConverter;

  public OrderEntity toEntity(OrderRequest request) {

    return OrderEntity.builder()
        .title(request.getTitle())
        .member(memberService.getMemberEntity(request.getUsername()))
        .address(request.getAddress())
        .recipient(request.getRecipient())
        .payCheck(request.getPayCheck())
        .cost(request.getCost())
        .build();
  }

  public List<OrderResponse> toResponseList(List<OrderEntity> entities) {

    return entities.stream().map(this::toResponse).toList();

  }

  public OrderResponse toResponse(OrderEntity order) {

    return OrderResponse.builder()
        .orderId(order.getOrderId())
        .title(order.getTitle())
        .address(order.getAddress())
        .phone(order.getPhone())
        .payCheck(order.getPayCheck())
        .status(order.getStatus())
        .recipient(order.getRecipient())
        .cost(order.getCost())
        .build();
  }

  public OrderDetails toOrderDetails(OrderDetailsEntity entity) {
    return OrderDetails.builder()
        .orderDetailsId(entity.getOrderDetailsId())
        .orderId(entity.getOrderDetailsId())
        .product(productEntityConverter.toProductSummary(entity.getProduct()))
        .count(entity.getCount())
        .status(entity.getStatus())
        .cost(entity.getCost())
        .build();
  }


}
