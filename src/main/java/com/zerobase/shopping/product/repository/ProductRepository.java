package com.zerobase.shopping.product.repository;

import com.zerobase.shopping.product.entity.ProductEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  Optional<ProductEntity> findByProductId(Long productId);
  Page<ProductEntity> findAllByDeleteYn(Pageable pageable, int deleteYn);
  Page<ProductEntity> findByTitleAndDeleteYn(Pageable pageable, String word, int deleteYn);
  Page<ProductEntity> findAllByTitleContainingAndDeleteYn(Pageable pageable, String word, int deleteYn);

}
