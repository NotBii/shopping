package com.zerobase.shopping.review.controller;

import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
  private final ReviewService reviewService;

//  public ResponseEntity<Long> write(@RequestBody WriteRequest reqeust, @AuthenticationPrincipal
//      MemberDetails member) {
//
//  }
}
