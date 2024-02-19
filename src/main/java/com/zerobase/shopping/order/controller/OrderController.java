package com.zerobase.shopping.order.controller;

import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.order.dto.StatusRequest;
import com.zerobase.shopping.order.dto.OrderDetails;
import com.zerobase.shopping.order.dto.OrderListRequest;
import com.zerobase.shopping.order.dto.OrderRequest;
import com.zerobase.shopping.order.dto.OrderResponse;
import com.zerobase.shopping.order.dto.PayCheck;
import com.zerobase.shopping.order.dto.UpdateDetailsRequest;
import com.zerobase.shopping.order.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;

  /**
   * 단일제품 주문(1항목)
   * @return 주문번호
   */
  @PostMapping("/make")
//  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> orderProduct(@RequestBody OrderRequest request,
      @AuthenticationPrincipal MemberDetails member) {
    Long result = orderService.addOrder(request, member.getUsername());

    return ResponseEntity.ok(result);
  }

  /**
   * 주문목록
   *
   */
  @GetMapping("/list")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> orderList(@RequestBody OrderListRequest request,
      @AuthenticationPrincipal MemberDetails member) {
    Page<OrderResponse> result = orderService.orderList(request, member);

    return ResponseEntity.ok(result);
  }

  /**
   * 주문상세정보보기
   * @return
   */
  @GetMapping("/details/{orderId}")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> orderDetails(@PathVariable long orderId, @AuthenticationPrincipal MemberDetails member) {

    List<OrderDetails> result = orderService.orderDetails(orderId, member);

    return ResponseEntity.ok(result);

  }

  /**
   * 결제확인
   */
  @PostMapping("/paycheck")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> paycheck(@RequestBody PayCheck request) {
    orderService.payCheck(request);

    return ResponseEntity.ok("결제확인완료");
  }

  /**
   * 주문상태변경 (상세정보 일괄변경 포함)
   */
  @PostMapping("/update")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> updateStatus(@RequestBody StatusRequest request) {
    orderService.updateOrder(request);

    return ResponseEntity.ok("상태변경완료");
  }
  /**
   * 주문상세정보 개별변경(개별 상품 상태등 관리)
   */
  @PostMapping("/update-details")
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public ResponseEntity<?> updateOrderDetails(@RequestBody List<UpdateDetailsRequest> requests) {
    orderService.updateDetails(requests);
    return ResponseEntity.ok("개별주문상태변경완료");
  }

  /**
   * 주문취소
   */
  @PostMapping("/cancel/{orderId}")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('Role_MANAGER')")
  public ResponseEntity<?> refund(@PathVariable Long orderId, @AuthenticationPrincipal MemberDetails member) {
    orderService.cancel(orderId, member);

    return ResponseEntity.ok("취소완료");
  }



}
