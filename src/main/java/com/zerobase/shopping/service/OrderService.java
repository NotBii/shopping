//package com.zerobase.shopping.service;
//
//import com.zerobase.shopping.dao.OrderDao;
//import com.zerobase.shopping.dao.ProductDao;
//import com.zerobase.shopping.dto.CartDto;
//import com.zerobase.shopping.dto.OrderDto;
//import com.zerobase.shopping.dto.ProductDto;
//import com.zerobase.shopping.model.OrderDetailsModel;
//import com.zerobase.shopping.model.OrderModel;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class OrderService {
//  private final ProductDao productDao;
//  private final OrderDao orderDao;
//
//  /**
//   * 수량체크
//   * @param request orderDetailsModel의 productNo
//   * @return 구입가능하면 True
//   */
//  public boolean checkStock(ProductDto productDto, int request) {
//
//    return request < productDto.getStock();
//  }
//
//  public long getPrice(OrderDetailsModel request) {
//    return request.getProductPrice() * request.getProductCount();
//  }
//
//  /**
//   *
//   * @param orderModel 주문정보
//   * @param orderDetailsModel 주문상세 : 상품번호와 수량만 있는 상태
//   */
//  @Transactional
//  public String addOrder(OrderModel orderModel, OrderDetailsModel orderDetailsModel) {
//
//    ProductDto productDto = productDao.getProductDetail(orderDetailsModel.getProductNo()); //상품정보 로드
//    if (!checkStock(productDto, orderDetailsModel.getProductCount())) {
//      throw new RuntimeException("재고가 부족합니다");
//    }
//    orderDetailsModel.setProductPrice(productDto.getPrice());//주문상세에 상품가격세팅
//
//    orderModel.setCost(getPrice(orderDetailsModel)); // 주문정보에 총가격 세팅
//
//    OrderDto orderDto = orderModel.toDto();
//    this.orderDao.addOrder(orderDto);
//    log.info(orderDto.toString());
//    orderDetailsModel.setOrderNo(orderDto.getNo()); // 주문정보 등록 후 no 받아서 세팅
//    this.orderDao.addOrderDetails(orderDetailsModel.toDto()); //주문상세 등록
//    String result = "주문번호: " +orderDto.getNo();
//
//    return result;
//  }
//
//  /**
//   * 카트 목록 주문하기
//   * @param cart cartDto가 담긴 리스트
//   * @param orderModel 주문정보
//   * @return 주문번호
//   */
//  @Transactional
//  public String addCartOrder(List<CartDto> cart, OrderModel orderModel) {
//
//    List<OrderDetailsModel> detailsList = new ArrayList<>();
//    long price = 0;
//    for (CartDto dto: cart) {
//      ProductDto product = productDao.getProductDetail(dto.getProductNo());
//      if (!checkStock(product, dto.getProductCount())) {
//        throw new RuntimeException(product.getNo() +" 재고가 부족합니다!");
//      }
//      OrderDetailsModel details = OrderDetailsModel.builder()
//          .name(orderModel.getName())
//          .productNo(product.getNo())
//          .productPrice(product.getPrice())
//          .productCount(dto.getProductCount())
//          .build();
//      detailsList.add(details);
//      price += product.getPrice() * dto.getProductCount();
//    }
//
//    orderModel.setCost(price);
//    int no = orderDao.addOrder(orderModel.toDto());
//
//    for (OrderDetailsModel model : detailsList) {
//      model.setOrderNo(no);
//      orderDao.addOrderDetails(model.toDto());
//    }
//    log.info("주문번호" + no);
//    String result = "주문번호: " + no;
//    return result;
//
//  }
//
//  public void payCheck(int no) {
//    log.info(no +"결제확인");
//    this.orderDao.payCheck(no);
//  }
//
//  public void updateStatus(OrderModel orderModel) {
//    log.info(orderModel.getNo() + "status 수정");
////    this.orderDao.updateStatus(orderModel.toDto());
//  }
//}
