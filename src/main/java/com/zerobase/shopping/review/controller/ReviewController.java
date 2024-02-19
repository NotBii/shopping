package com.zerobase.shopping.review.controller;

import com.zerobase.shopping.commons.dto.SearchOption;
import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.review.dto.ReviewDetail;
import com.zerobase.shopping.review.dto.ReviewListResponse;
import com.zerobase.shopping.review.dto.ReviewWriteRequest;
import com.zerobase.shopping.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping("/write")
  public ResponseEntity<Long> write(@RequestBody ReviewWriteRequest request,
      @AuthenticationPrincipal
      MemberDetails member) {
    long result = reviewService.write(request, member);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/list/{productId}")
  public ResponseEntity<Page<ReviewListResponse>> list(@PathVariable long productId,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestBody SearchOption search) {

    Page<ReviewListResponse> result = reviewService.list(productId, page, 0, search);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/list/{username}")
  public ResponseEntity<Page<ReviewListResponse>> userReviewList(@PathVariable String username, @RequestParam(name = "page", defaultValue = "0", required = false) int page, @RequestBody SearchOption search) {

    Page<ReviewListResponse> result = reviewService.list(username, page, 0, search);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/deleted-list/{username}")
  public ResponseEntity<Page<ReviewListResponse>> deletedUserReviewList(@PathVariable String username, @RequestParam(name = "page", defaultValue = "0", required = false) int page, @RequestBody SearchOption search) {

    Page<ReviewListResponse> result = reviewService.list(username, page, 1, search);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/deleted-list/{productId}")
  @PreAuthorize("hasRole('Role_MANAGER')")
  public ResponseEntity<Page<ReviewListResponse>> deletedList(@PathVariable long productId,
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestBody SearchOption search) {

    Page<ReviewListResponse> result = reviewService.list(productId, page, 1, search);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/details/{reviewId}")
  public ResponseEntity<ReviewDetail> detail(@PathVariable long reviewId) {
    ReviewDetail result = reviewService.detail(reviewId, 0);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/deleted-details/{reviewId}")
  @PreAuthorize("hasRole('Role_MANAGER')")
  public ResponseEntity<ReviewDetail> deleteDetail(@PathVariable long reviewId) {
    ReviewDetail result = reviewService.detail(reviewId, 1);

    return ResponseEntity.ok(result);
  }

  @PostMapping("/update")
  public ResponseEntity<Long> update(@RequestBody ReviewWriteRequest request
      , @AuthenticationPrincipal MemberDetails member) {
    Long result = reviewService.update(request, member);

    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/delete/{reviewId}")
  public ResponseEntity<String> delete(@PathVariable Long reviewId, @AuthenticationPrincipal MemberDetails member) {
    reviewService.changeIsDeleted(reviewId, member, 1);
    return ResponseEntity.ok("삭제 완료");
  }

  /**
   * 이미 추천 한 상태면 추천 삭제, 아니라면 추천
   */
  @GetMapping("/recommendation/{reviewId}")
  public ResponseEntity<String> changeRecommendation(@PathVariable Long reviewId,
      @AuthenticationPrincipal MemberDetails member) {
    String result = reviewService.changeRecommendation(reviewId, member);

    return ResponseEntity.ok(result);
  }

  /**
   *
   */






}
