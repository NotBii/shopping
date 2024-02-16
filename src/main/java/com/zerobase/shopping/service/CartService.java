//package com.zerobase.shopping.service;
//
//import com.zerobase.shopping.dao.CartDao;
//import com.zerobase.shopping.dao.ProductDao;
//import com.zerobase.shopping.dto.CartDto;
//import com.zerobase.shopping.model.CartModel;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CartService {
//
//  private final CartDao cartDao;
//  private final ProductDao productDao;
//
//
//  public Optional<CartDto> checkSameProduct(CartDto cartDto) {
//      return cartDao.checkSameProduct(cartDto);
//  }
//
//  /**
//   * 수량체크
//   * @param cartDto
//   * @return 구입가능하면 True
//   */
//  public boolean checkStock(CartDto cartDto) {
//
//    return cartDto.getProductCount() < productDao.checkProductStock(cartDto.getProductNo());
//
//  }
//
//  /**
//   *
//   * @param cartModel
//   * @return 상품 추가 혹은 수량 수정 문구 반환
//   */
//  public String addCart(CartModel cartModel) {
//    String result = "";
//    CartDto cartDto = cartModel.toDto();
//
//    if (!checkStock(cartDto) ) {
//      throw new RuntimeException("재고가 부족합니다");
//    }
//
//    Optional<CartDto> cartCheck = checkSameProduct(cartDto);
//
//    if (cartCheck.isEmpty()) {
//      cartDao.addCart(cartDto);
//      result = "상품 추가 완료!";
//      log.info("addCart-> " + cartDto);
//    } else {
//      CartDto originalCart = cartCheck.get();
//      int no = originalCart.getNo();
//      int newCount = originalCart.getProductCount() + cartDto.getProductCount();
//      cartDao.updateCart(no, newCount);
//      result = "기존장바구니에 수량을 더했습니다";
//      log.info("updateCart-> " + cartDto);
//    }
//
//    return result;
//  }
//
//  /**
//   * 상품수량수정
//   * @param cartDto
//   */
//  public void updateCart(CartDto cartDto) {
//    if (!checkStock(cartDto)) {
//      throw new RuntimeException("재고가 부족합니다");
//    }
//      cartDao.updateCart(cartDto.getNo(), cartDto.getProductCount());
//  }
//
//  /**
//   * 상품삭제
//   * @param cartDto
//   */
//  public void deleteCart(CartDto cartDto) {
//    cartDao.deleteCart(cartDto);
//    log.info(cartDto.toString());
//  }
//
//  public Optional<CartDto> showCart(String name) {
//    Optional<CartDto> result = cartDao.showCart(name);
//
//    return result;
//  }
//}
