package com.zerobase.shopping.inquiry.controller;

import com.zerobase.shopping.inquiry.dto.InquiryDetail;
import com.zerobase.shopping.inquiry.dto.ListResponse;
import com.zerobase.shopping.commons.dto.SearchOption;
import com.zerobase.shopping.inquiry.dto.WriteRequest;
import com.zerobase.shopping.inquiry.service.InquiryService;
import com.zerobase.shopping.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {

  private final InquiryService inquiryService;

  @PostMapping("/write")
  public ResponseEntity<Long> write(@RequestBody WriteRequest request, @AuthenticationPrincipal
  MemberDetails member) {
    Long result = inquiryService.write(request, member);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/{productId}/list")
  public ResponseEntity<Page<ListResponse>> list(@PathVariable Long productId,
      @RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestBody
      SearchOption search) {
    Page<ListResponse> result = inquiryService.inquiryList(page, productId, search);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/{inquiryId}")
  public ResponseEntity<InquiryDetail> detail(@PathVariable Long inquiryId,
      @AuthenticationPrincipal MemberDetails member) {
    InquiryDetail result = inquiryService.detail(inquiryId, member);

    return ResponseEntity.ok(result);
  }

  @PostMapping("/update")
  public ResponseEntity<Long> update(@RequestBody WriteRequest request,
      @AuthenticationPrincipal MemberDetails member) {
    Long result = inquiryService.update(request, member);

    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/delete/{inquiryId}")
  public ResponseEntity<String> delete(@PathVariable Long inquiryId,
      @AuthenticationPrincipal MemberDetails member) {
    inquiryService.changeIsDeleted(inquiryId, member, 1);

    return ResponseEntity.ok("삭제완료");
  }

  @PostMapping("/recover/{inquiryId}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<String> recover(@PathVariable Long inquiryId,
      @AuthenticationPrincipal MemberDetails member) {
    inquiryService.changeIsDeleted(inquiryId, member, 0);

    return ResponseEntity.ok("복구완료");
  }

}
