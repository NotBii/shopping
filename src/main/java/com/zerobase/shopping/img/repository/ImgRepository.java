package com.zerobase.shopping.img.repository;

import com.zerobase.shopping.img.entity.ImgEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<ImgEntity, Long> {
  Optional<ImgEntity> findByImgId(Long imgId);
}
