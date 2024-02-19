package com.zerobase.shopping.order.repository;

import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.order.entity.OrderDetailsEntity;
import com.zerobase.shopping.order.entity.OrderEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {

  List<OrderDetailsEntity> findByOrder(OrderEntity entity);
  boolean findByMemberAndProduct(MemberEntity member, ProductEntity product);
  Optional<OrderDetailsEntity> findByOrderDetailsId(Long orderDetailId);
}
