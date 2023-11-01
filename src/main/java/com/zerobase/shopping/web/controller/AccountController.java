package com.zerobase.shopping.web.controller;

import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.model.AccountModel;
import com.zerobase.shopping.security.TokenProvider;
import com.zerobase.shopping.service.AccountService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  private final TokenProvider tokenProvider;

  //회원가입

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody AccountModel.SignUp request) {

    AccountDto result = this.accountService.signup(request);

    return ResponseEntity.ok(result);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody AccountModel.SignIn request) {
    AccountDto account = this.accountService.authenticate(request);
    String token = this.tokenProvider.generateToken(account.getUserid(), account.getRole());

    log.info("login");
    return ResponseEntity.ok(token);
  }
  //id 중복체크

  @GetMapping("/id-check")
  public ResponseEntity<?> idCheck(@RequestParam String userid) {

    boolean result = this.accountService.idCheck(userid);

    return ResponseEntity.ok(result);

  }

  //email 중복체크
  @GetMapping ("/mail-check")
  public ResponseEntity<?> mailCheck(@RequestParam String mail) {

    boolean result = this.accountService.mailCheck(mail);

    return ResponseEntity.ok(result);
  }
  //닉네임 중복체크

  @GetMapping ("/nickname-check")
  public ResponseEntity<?> nicknameCheck(@RequestParam String nickname) {

    boolean result = this.accountService.nicknameCheck(nickname);

    return ResponseEntity.ok(result);
  }

  /**
   * 로그인시 발급된 토큰에서 유저 정보(이름, role)
   * @param user
   *
   */

  //회원 정보 조회
  @GetMapping ("/user-details")
  public ResponseEntity<?> userDetails(@AuthenticationPrincipal AccountModel user) {
    String userid = user.getUsername();
    Optional<AccountDto> result = this.accountService.userDetails(userid);

    return ResponseEntity.ok(result);
  }

  //회원 정보 수정
  @PostMapping("/update-profile")
  public ResponseEntity<?> updateProfile(@RequestBody AccountDto accountDto) {

    AccountDto result = this.accountService.updateProfile(accountDto);

    return ResponseEntity.ok(result);
  }

  //회원 탈퇴
  @DeleteMapping("/resign")
  public ResponseEntity<?> resign(@RequestBody AccountDto accountDto) {

    AccountDto result = this.accountService.resign(accountDto);

    return ResponseEntity.ok(accountDto);
  }
  //id 찾기


  //pw 찾기

}
