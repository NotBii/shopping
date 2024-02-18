package com.zerobase.shopping.order.repository;

import com.zerobase.shopping.order.entity.OrderDetailsEntity;
import com.zerobase.shopping.order.entity.OrderEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {

  List<OrderDetailsEntity> findByOrderId(OrderEntity entity);
  Optional<OrderDetailsEntity> findByOrderDetailsId(Long orderDetailId);
}
