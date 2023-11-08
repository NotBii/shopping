package com.zerobase.shopping.service;

import com.zerobase.shopping.dao.ProductDao;
import com.zerobase.shopping.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductDao productDao;

  public int saveProduct(ProductDto productDto) {
    this.productDao.saveProduct(productDto);
    return productDto.getNo();
  }

}
