package com.zerobase.shopping.review.service;

import com.zerobase.shopping.commons.GetEntity;
import com.zerobase.shopping.commons.PageOptions;
import com.zerobase.shopping.commons.dto.SearchOption;
import com.zerobase.shopping.commons.exception.impl.AlreadyReviewed;
import com.zerobase.shopping.commons.exception.impl.NoAuth;
import com.zerobase.shopping.commons.exception.impl.UserNotMatch;
import com.zerobase.shopping.img.entity.ImgEntity;
import com.zerobase.shopping.img.service.ImgEntityConverter;
import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
import com.zerobase.shopping.product.repository.ProductRepository;
import com.zerobase.shopping.review.dto.ReviewDetail;
import com.zerobase.shopping.review.dto.ReviewListResponse;
import com.zerobase.shopping.review.dto.ReviewWriteRequest;
import com.zerobase.shopping.review.entity.RecommendEntity;
import com.zerobase.shopping.review.entity.ReviewEntity;
import com.zerobase.shopping.review.repository.RecommendRepository;
import com.zerobase.shopping.review.repository.ReviewRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepository;
  private final GetEntity getEntity;
  private final ReviewEntityConverter reviewConverter;
  private final RecommendRepository recommendRepository;

  /**
   * 쓰기
   *
   * @param request 글쓰기요청dto
   * @param member  로그인정보
   * @return
   */
  @Transactional
  public long write(ReviewWriteRequest request, MemberDetails member) {
    ProductEntity productEntity = getEntity.productEntity(request.getProductId());
    MemberEntity memberEntity = getEntity.memberEntity(member.getUsername());

    checkReview(memberEntity, productEntity);
    checkOrderDetail(memberEntity, productEntity);

    request.setWriter(member.getUsername());

    ReviewEntity reviewEntity = reviewConverter.toEntity(request);
    reviewRepository.save(reviewEntity);
    productEntity.updateScore(request.getScore(), 1);
    productRepository.save(productEntity);

    return reviewEntity.getReviewId();
  }

  /**
   * 목록보기
   *
   * @param productId 상품id
   * @param page      현재페이지
   * @param isDeleted 삭제여부
   * @param search
   * @return
   */
  public Page<ReviewListResponse> list(long productId, int page, int isDeleted,
      SearchOption search) {
    ProductEntity product = getEntity.productEntity(productId);
    String type = search.getType();
    String word = search.getWord();
    Pageable pageable = PageOptions.getPageable(search, page, 10);

    Page<ReviewEntity> list = switch (type) {
      case "writer" ->
          reviewRepository.findByProductAndIsDeletedAndWriter(pageable, product, isDeleted,
              getEntity.memberEntity(word));
      default -> reviewRepository.findByProductAndIsDeleted(pageable, product, isDeleted);
    };

    return list.map(reviewConverter::toReviewListResponse);
  }

  /**
   * 개별유저 전체 작성 리뷰
   *
   * @param username
   * @param page
   * @param isdeleted
   * @param search
   * @return
   */
  public Page<ReviewListResponse> list(String username, int page, int isdeleted,
      SearchOption search) {
    Pageable pageable = PageOptions.getPageable(search, page, 10);
    MemberEntity member = getEntity.memberEntity(username);

    return reviewRepository.findByWriterAndIsDeleted(pageable, member, isdeleted)
        .map(reviewConverter::toReviewListResponse);
  }

  /**
   * 상세보기
   *
   * @param reviewId  리뷰번호
   * @param isDeleted 삭제여부
   * @return
   */
  public ReviewDetail detail(long reviewId, int isDeleted) {
    ReviewEntity entity = getEntity.reviewEntity(reviewId, isDeleted);
    entity.readCountUp();
    reviewRepository.save(entity);
    return reviewConverter.toReviewDetail(entity);
  }

  /**
   * 수정
   *
   * @param request 글쓰기요청dto
   * @param member  로그인 정보
   * @return 글번호
   */
  @Transactional
  public Long update(ReviewWriteRequest request, MemberDetails member) {
    HashMap<String, Object> map = checkUser(request.getReviewId(), member);
    ReviewEntity review = (ReviewEntity) map.get("review");
    ProductEntity product = (ProductEntity) map.get("product");

    int scoreChange = review.getScore() - request.getScore();

    product.updateScore(scoreChange, 0);
    productRepository.save(product);

    List<ImgEntity> imgs = ImgEntityConverter.dtoListToEntityList(request.getImgList());

    review.update(request, imgs);
    reviewRepository.save(review);

    return review.getReviewId();
  }

  /**
   * 삭제
   *
   * @param reviewId 리뷰번호
   * @param member   로그인정보
   */
  @Transactional
  public void changeIsDeleted(long reviewId, MemberDetails member, int deleteYn) {
    HashMap<String, Object> map = checkUser(reviewId, member);
    ReviewEntity review = (ReviewEntity) map.get("review");
    ProductEntity product = (ProductEntity) map.get("product");

    if (deleteYn != 0) {
      product.updateScore((review.getScore() - product.getTotalScore()), -1);
    } else {
      product.updateScore(review.getScore(), 1);
    }
      productRepository.save(product);
      review.changeIsDeleted(deleteYn);
      reviewRepository.save(review);
  }

  /**
   * 추천증감
   *
   * @param reviewId 리뷰번호
   * @param member   로그인 회원
   * @return String
   */
  @Transactional
  public String changeRecommendation(Long reviewId, MemberDetails member) {
    ReviewEntity reviewEntity = getEntity.reviewEntity(reviewId, 0);
    MemberEntity memberEntity = getEntity.memberEntity(member.getUsername());
    Optional<RecommendEntity> op = getEntity.recommendEntity(memberEntity);
    String result;

    if (op.isPresent()) {
      recommendRepository.delete(RecommendEntity.builder()
          .member(memberEntity)
          .review(reviewEntity)
          .build());
      reviewEntity.changeRecommended(-1);
      result = "추천취소";
    } else {
      recommendRepository.save(RecommendEntity.builder()
          .member(memberEntity)
          .review(reviewEntity)
          .build());
      reviewEntity.changeRecommended(1);
      result = "추천!";
    }

    return result;
  }

  private HashMap<String, Object> checkUser(Long reviewId, MemberDetails member) {
    ReviewEntity reviewEntity = getEntity.reviewEntity(reviewId, 0);
    MemberEntity memberEntity = getEntity.memberEntity(member.getUsername());
    if (!reviewEntity.getWriter().getUsername().equals(memberEntity.getUsername())
        && !member.getRole().equals("USER_MANAGER")) {
      throw new UserNotMatch();
    }

    HashMap<String, Object> map = new HashMap<>();
    map.put("review", reviewEntity);
    map.put("member", memberEntity);

    return map;
  }

  private void checkReview(MemberEntity member, ProductEntity product) {

    Optional<ReviewEntity> checkReview = getEntity.reviewEntity(member, product, 0);

    if (checkReview.isPresent()) {
      throw new AlreadyReviewed();
    }
  }

  private void checkOrderDetail(MemberEntity member, ProductEntity product) {
    boolean isExist = getEntity.isExistOrderDetails(member, product);

    if (!isExist) {
      throw new NoAuth();
    }

  }

}
