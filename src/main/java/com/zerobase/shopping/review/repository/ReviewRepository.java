package com.zerobase.shopping.review.repository;

import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.review.entity.ReviewEntity;
import com.zerobase.shopping.review.repository.queryDsl.ReviewRepositoryCustom;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>,
    ReviewRepositoryCustom {

  Optional<ReviewEntity> findByReviewIdAndIsDeleted(long reviewId, int isDelted);
  Page<ReviewEntity> findByProductAndIsDeleted(Pageable pageable, ProductEntity product, int isDeleted);
  Page<ReviewEntity> findByProductAndIsDeletedAndWriter(Pageable pageable, ProductEntity product, int isDeleted, MemberEntity member);
  Page<ReviewEntity> findByWriterAndIsDeleted(Pageable pageable, MemberEntity member, int isDeleted);
  Optional<ReviewEntity> findByWriterAndProductAndIsDeleted(MemberEntity member, ProductEntity product, int isDeleted);

}
