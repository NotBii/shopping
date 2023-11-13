package com.zerobase.shopping.service;

import com.zerobase.shopping.dao.ProductDao;
import com.zerobase.shopping.dto.ProductDto;
import com.zerobase.shopping.dto.SearchDto;
import java.util.*;
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

  public ProductDto getProductDetail(int no) {
    return this.productDao.getProductDetail(no);
  }

  public List<ProductDto> getProductList(final SearchDto searchDto) {
    List<ProductDto> result = this.productDao.getProductList(searchDto);

    return result;
  }

  public ProductDto updateProduct(ProductDto productDto) {
    this.productDao.updateProduct(productDto);
    ProductDto result = this.productDao.getProductDetail(productDto.getNo());
    return result;
  }

  public void deleteProduct(int no) {
    this.productDao.deleteProduct(no);
  }

  public void updateSalesRate(int no) {
    this.productDao.updateSalesRate(no);
  }
}
