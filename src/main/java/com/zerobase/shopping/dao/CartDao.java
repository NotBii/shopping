package com.zerobase.shopping.dao;

import com.zerobase.shopping.dto.CartDto;
import com.zerobase.shopping.dto.SearchDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CartDao {

  void addCart(CartDto cartDto);
  Optional<CartDto> checkSameProduct(CartDto cartDto);
  void updateCart(int no, int count);

  void deleteCart(CartDto cartDto);

  Optional<CartDto> showCart(String name);

}
