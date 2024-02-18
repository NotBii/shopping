package com.zerobase.shopping.commons;

import com.zerobase.shopping.commons.exception.impl.MemberNotFound;
import com.zerobase.shopping.commons.exception.impl.NoArticle;
import com.zerobase.shopping.img.repository.ImgRepository;
import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.inquiry.repository.InquiryRepository;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.member.repository.MemberRepository;
import com.zerobase.shopping.order.repository.OrderDetailsRepository;
import com.zerobase.shopping.order.repository.OrderRepository;
import com.zerobase.shopping.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetEntity {
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderDetailsRepository orderDetailsRepository;
  private final InquiryRepository inquiryRepository;
  private final ImgRepository imgRepository;

  public MemberEntity memberEntity(String username) {
    return memberRepository.findByUsername(username).orElseThrow(MemberNotFound::new);
  }
  public InquiryEntity inquiryEntity(Long id) {
    return inquiryRepository.findById(id).orElseThrow(NoArticle::new);
  }
}
