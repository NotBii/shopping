package com.zerobase.shopping.dao;

import com.zerobase.shopping.dto.ProductDto;
import com.zerobase.shopping.model.ProductModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ProductDao {

  void saveProduct(ProductDto productdto);

}
