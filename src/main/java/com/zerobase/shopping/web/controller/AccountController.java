package com.zerobase.shopping.web.controller;

import com.zerobase.shopping.service.AccountService;
import com.zerobase.shopping.vo.AccountVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor

public class AccountController {

  private final AccountService accountService;
  //회원가입

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody AccountVo accountVo) {

    AccountVo result = this.accountService.signup(accountVo);

    return ResponseEntity.ok(result);
  }
  //id 중복체크

  @GetMapping("/idcheck")
  public ResponseEntity<?> idCheck(@RequestParam String userid) {

    boolean result = this.accountService.idCheck(userid);

    return ResponseEntity.ok(result);

  }

  //email 중복체크
  @GetMapping ("/mailcheck")
  public ResponseEntity<?> mailCheck(@RequestParam String mail) {

    boolean result = this.accountService.mailCheck(mail);

    return ResponseEntity.ok(result);
  }
  //닉네임 중복체크

  //회원 정보 조회
  @GetMapping ("/user-details")
  public ResponseEntity<?> userDetails(@RequestParam String userid) {

    AccountVo result = this.accountService.userDetails(userid);

    return ResponseEntity.ok(result);
  }

  //회원 정보 수정
  @PostMapping("/update-profile")
  public ResponseEntity<?> updateProfile(@RequestBody AccountVo accountVo) {

    AccountVo result = this.accountService.updateProfile(accountVo);

    return ResponseEntity.ok(result);
  }

  //회원 탈퇴
  //id 찾기
  //pw 찾기

}
