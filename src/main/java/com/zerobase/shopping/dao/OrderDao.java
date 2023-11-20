package com.zerobase.shopping.dao;

import com.zerobase.shopping.dto.OrderDetailsDto;
import com.zerobase.shopping.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderDao {
  int addOrder(OrderDto orderDto);
  void addOrderDetails(OrderDetailsDto orderDetailsDto);
  void payCheck(int no);
}
