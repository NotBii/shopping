package com.zerobase.shopping.web.controller;

import com.zerobase.shopping.dto.CartDto;
import com.zerobase.shopping.dto.OrderDetailsDto;
import com.zerobase.shopping.model.AccountModel;
import com.zerobase.shopping.model.OrderDetailsModel;
import com.zerobase.shopping.model.OrderModel;
import com.zerobase.shopping.service.CartService;
import com.zerobase.shopping.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;

  /**
   * 단일제품 주문(1항목)
   * @param orderModel  주문정보
   * @param orderDetailsModel productNo:상품번호, productCount:상품수량
   * @param user
   * @return
   */
  @PostMapping("/product-order")
  public ResponseEntity<?> orderProduct(@RequestPart(value ="order-model") OrderModel orderModel
      ,@RequestPart(value ="order-details") OrderDetailsModel orderDetailsModel
      ,@AuthenticationPrincipal AccountModel user) {
    orderModel.setName(user.getUserId());
    orderDetailsModel.setName(user.getUserId());

    String result = this.orderService.addOrder(orderModel, orderDetailsModel);

    return ResponseEntity.ok(result);
  }

  /**
   * 장바구니 주문
   * @param cart cartDto리스트
   * @param orderModel 주문정보
   * @param user
   * @return
   */
  @PostMapping("/cart-order")
  public ResponseEntity<?> orderCart(@RequestPart(value="cart") List<CartDto> cart,
      @RequestPart(value="order-model") OrderModel orderModel,
      @AuthenticationPrincipal AccountModel user) {
    orderModel.setName(user.getUserId());

    String result = this.orderService.addCartOrder(cart, orderModel);

    return ResponseEntity.ok(result);
  }

  /**
   * 결제확인
   * @param no 주문번호
   * @param user 매니저 권한 로그인
   * @return
   */
  @GetMapping("/pay-check")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> paycheck(@RequestParam int no, @AuthenticationPrincipal AccountModel user) {
    this.orderService.payCheck(no);

    return ResponseEntity.ok(null);
  }

  /**
   *
   * @param orderModel
   * @return
   */
  @PostMapping("/update-status")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> updateStatus(@RequestBody OrderModel orderModel) {
    this.orderService.updateStatus(orderModel);
    return ResponseEntity.ok(null);
  }

}
