package com.zerobase.shopping.cart.repository;

import com.zerobase.shopping.cart.entity.CartEntity;
import com.zerobase.shopping.cart.entity.CartProductEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {

  Optional<CartProductEntity> findByCartAndProduct(CartEntity cart, ProductEntity product);
  List<CartProductEntity> findByCart(CartEntity cart);

}
