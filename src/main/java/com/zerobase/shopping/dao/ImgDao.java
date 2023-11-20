package com.zerobase.shopping.dao;

import com.zerobase.shopping.dto.ImgDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ImgDao {
  void saveAll(List<ImgDto> imgs);

  List<ImgDto> findAllByNo(List<Long> no);

  void deleteAllByNo(List<Long> no);


}
