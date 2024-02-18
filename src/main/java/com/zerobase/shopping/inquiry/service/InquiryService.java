package com.zerobase.shopping.inquiry.service;

import com.zerobase.shopping.inquiry.dto.ListResponse;
import com.zerobase.shopping.inquiry.dto.WriteRequest;
import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.inquiry.repository.InquiryRepository;
import com.zerobase.shopping.member.dto.MemberDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryService {
  private final InquiryRepository inquiryRepository;
  private final InquiryEntityConverter inquiryConverter;

  /**
   * 문의쓰기
   * @param inquiryRequest
   * @param member
   * @return
   */
  public Long write (WriteRequest inquiryRequest, MemberDetails member) {
    inquiryRequest.setWriter(member.getUsername());
    InquiryEntity entity = inquiryConverter.toEntity(inquiryRequest);
    inquiryRepository.save(entity);

    return entity.getInquiryId();
  }

  /**
   * 문의목록보기
   * @param page
   * @param productId
   * @return
   */
  public Page<ListResponse> inquiryList(int page, Long productId) {
    Pageable pageable = PageRequest.of(page, 10, Sort.by("inquiryId").descending());

    return inquiryRepository.findByProductIdAndDeleteYn(pageable, productId, 0).map(inquiryConverter::toListResponse);
  }

  public InquiryDetail


}
