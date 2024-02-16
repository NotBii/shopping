//package com.zerobase.shopping.web.controller;
//
//import com.zerobase.shopping.dto.CartDto;
//import com.zerobase.shopping.model.MemberModel;
//import com.zerobase.shopping.model.CartModel;
//import com.zerobase.shopping.service.CartService;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//@RequestMapping("/cart")
//@RequiredArgsConstructor
//
//public class CartController {
//  private final CartService cartService;
//
//  @GetMapping("/addCart")
//  public ResponseEntity<?> addCart(@RequestParam(value = "product-no") int productNo, @RequestParam(value = "count") int count,
//                            @AuthenticationPrincipal MemberModel user) {
//    CartModel cartModel = CartModel.builder()
//        .name(user.getUserId())
//        .productNo(productNo)
//        .productCount(count)
//        .build();
//    String result = cartService.addCart(cartModel);
//
//  return ResponseEntity.ok(result);
//
//  }
//
//  @PostMapping("/updateCart")
//  public ResponseEntity<?> updateCart(@RequestBody CartModel cartModel, @AuthenticationPrincipal MemberModel user) {
//    cartModel.setName(user.getUserId());
//    cartService.updateCart(cartModel.toDto());
//    return ResponseEntity.ok(null);
//  }
//
//  @DeleteMapping("/deleteCart")
//  public ResponseEntity<?> deleteCart(@RequestBody CartModel cartModel, @AuthenticationPrincipal MemberModel user) {
//    cartModel.setName(user.getUserId());
//    log.info(cartModel.toString());
//    cartService.deleteCart(cartModel.toDto());
//    return ResponseEntity.ok(null);
//  }
//
//  @GetMapping("/showCart")
//  public ResponseEntity<?> showCart(@AuthenticationPrincipal MemberModel user) {
//
//    Optional<CartDto> result = cartService.showCart(user.getUserId());
//
//    return ResponseEntity.ok(result);
//  }
//
////  @PostMapping("/to-order")
////  public ResponseEntity<?> cartToOrder(@AuthenticationPrincipal AccountModel user) {
////
////
////  }
//}
