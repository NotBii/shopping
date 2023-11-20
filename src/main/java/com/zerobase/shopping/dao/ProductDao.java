package com.zerobase.shopping.dao;

import com.zerobase.shopping.dto.ProductDto;
import com.zerobase.shopping.dto.SearchDto;
import com.zerobase.shopping.model.ProductModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ProductDao {

  void saveProduct(ProductDto productdto);

  ProductDto getProductDetail(int no);
  List<ProductDto> getProductList(SearchDto searchDto);

  void updateProduct(ProductDto productDto);
  void deleteProduct(int no);

  int count (SearchDto searchDto);

  void updateSalesRate(int no);


  int checkProductStock (int no);

}
