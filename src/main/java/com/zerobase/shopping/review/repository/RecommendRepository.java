package com.zerobase.shopping.review.repository;

import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.review.entity.RecommendEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRepository extends JpaRepository<RecommendEntity, Long> {
  Optional<RecommendEntity> findByMember(MemberEntity member);

}
