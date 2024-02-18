package com.zerobase.shopping.order.repository;

import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.order.entity.OrderEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
  Optional<OrderEntity> findByOrderId (Long orderId);
  Page<OrderEntity> findByMemberId(Pageable pageable, MemberEntity member);
}
