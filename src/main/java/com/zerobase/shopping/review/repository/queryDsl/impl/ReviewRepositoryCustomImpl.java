package com.zerobase.shopping.review.repository.queryDsl.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.shopping.review.repository.queryDsl.ReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {
  private final JPAQueryFactory jpaQueryFactory;

}
