package com.zerobase.shopping.cart.controller;

import com.zerobase.shopping.cart.dto.CartProduct;
import com.zerobase.shopping.cart.service.CartService;
import com.zerobase.shopping.member.dto.MemberDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER')")
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;
  @PostMapping("/add")
  public ResponseEntity<String> add(@RequestBody CartProduct request,
                            @AuthenticationPrincipal MemberDetails member) {
    cartService.add(request.getProductId(), request.getCount(), member.getUsername());

  return ResponseEntity.ok("저장성공");

  }
  @GetMapping("/show")
  public ResponseEntity<List<CartProduct>> showAll(@AuthenticationPrincipal MemberDetails member) {

    List<CartProduct> result = cartService.showAll(member.getUsername());

    return ResponseEntity.ok(result);
  }

  @PostMapping("/update-count")
  public ResponseEntity<String> updateCount(@RequestBody CartProduct request, @AuthenticationPrincipal MemberDetails member) {

    cartService.changeProductCount(member.getUsername(), request.getCartProductId(), request.getCount());

    return ResponseEntity.ok("변경완료");
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> delete(@RequestBody CartProduct request, @AuthenticationPrincipal MemberDetails member) {

    cartService.changeProductCount(member.getUsername(), request.getCartProductId(), request.getCount());
    return ResponseEntity.ok("삭제완료");
  }


//  @PostMapping("/to-order")
//  public ResponseEntity<?> cartToOrder(@AuthenticationPrincipal AccountModel user) {
//
//
//  }
}
