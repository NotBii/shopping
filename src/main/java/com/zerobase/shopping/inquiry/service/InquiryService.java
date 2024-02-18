package com.zerobase.shopping.inquiry.service;

import com.zerobase.shopping.commons.GetEntity;
import com.zerobase.shopping.commons.exception.impl.NoAuth;
import com.zerobase.shopping.img.entity.ImgEntity;
import com.zerobase.shopping.img.service.ImgEntityConverter;
import com.zerobase.shopping.inquiry.dto.InquiryDetail;
import com.zerobase.shopping.inquiry.dto.ListResponse;
import com.zerobase.shopping.inquiry.dto.WriteRequest;
import com.zerobase.shopping.inquiry.entity.InquiryEntity;
import com.zerobase.shopping.inquiry.repository.InquiryRepository;
import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.product.service.ProductService;
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
  private final ProductService productService;
  private final InquiryEntityConverter inquiryConverter;
  private final GetEntity getEntity;


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
    ProductEntity productEntity = productService.getProductEntity(productId);
    return inquiryRepository.findByProductIdAndDeleteYn(pageable, productEntity, 0).map(inquiryConverter::toListResponse);
  }

  /**
   * 상세보기
   * @param inquiryId : 글 id
   * @return inquiryDetail
   */

  public InquiryDetail detail(Long inquiryId) {
    return inquiryConverter.toDetail(getEntity.inquiryEntity(inquiryId));
  }

  /**
   * 글 수정
   * @param request : 글 저장 요청 DTO
   * @param member : 로그인정보
   * @return 글 상세 dto
   */
  public Long update(WriteRequest request, MemberDetails member) {

    InquiryEntity inquiryEntity = getEntity.inquiryEntity(request.getInquiryId());

    checkUser(inquiryEntity, member, 0);

    List<ImgEntity> imgs = ImgEntityConverter.dtoListToEntityList(request.getImgList());
    System.out.println(request.getTitle());
    inquiryEntity.update(request, imgs);
    inquiryRepository.save(inquiryEntity);


    return inquiryEntity.getInquiryId();
  }

  public void changeDeleteYn (Long inquiryId, MemberDetails member, int deleteYn) {
    InquiryEntity inquiryEntity = getEntity.inquiryEntity(inquiryId);

    checkUser(inquiryEntity, member,0);

    inquiryEntity.changeDeleteYn(deleteYn);
  }



  /**
   *
   * @param type : 0이면 문의글, 1이면 댓글
   */
  private void checkUser(InquiryEntity inquiryEntity, MemberDetails member, int type) {
    if (type == 0) {
      if (!member.getUsername().equals(inquiryEntity.getWriter().getUsername()) && !member.getRole()
          .equals("ROLE_MANAGER")) {
        throw new NoAuth();
      }
    } else if (type == 1) {

    }

  }


}
