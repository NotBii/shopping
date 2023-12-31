package com.zerobase.shopping.web.controller;

import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.dto.MailDto;
import com.zerobase.shopping.model.AccountModel;
import com.zerobase.shopping.security.TokenProvider;
import com.zerobase.shopping.service.AccountService;
import com.zerobase.shopping.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
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
  private final MailService mailService;
  private final TokenProvider tokenProvider;



  //회원가입
  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody AccountModel.SignUp request) {

    AccountDto result = this.accountService.signup(request);

    return ResponseEntity.ok(result);
  }

  //로그인
  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody AccountModel.SignIn request) {
    AccountDto account = this.accountService.authenticate(request);
    String token = this.tokenProvider.generateToken(account.getUserId(), account.getRole());

    log.info("login");
    return ResponseEntity.ok(token);
  }

  //로그아웃
  @GetMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }

    return ResponseEntity.ok("로그아웃되었습니다");

  }

  //id 중복체크
  @GetMapping("/check-id")
  public ResponseEntity<?> idCheck(@RequestParam String userid) {

    boolean result = this.accountService.idCheck(userid);

    return ResponseEntity.ok(result);

  }

  //email 중복체크
  @GetMapping ("/check-mail")
  public ResponseEntity<?> mailCheck(@RequestParam String mail) {

    boolean result = this.accountService.mailCheck(mail);

    return ResponseEntity.ok(result);
  }

  //닉네임 중복체크
  @GetMapping ("/check-nickname")
  public ResponseEntity<?> nicknameCheck(@RequestParam String nickname) {

    boolean result = this.accountService.nicknameCheck(nickname);

    return ResponseEntity.ok(result);
  }



  //회원 정보 조회
  @GetMapping ("/user-details")
  public ResponseEntity<?> userDetails(@RequestParam String userid) {

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

    return ResponseEntity.ok(result);
  }
  //id 찾기
  @GetMapping("/find-id")
  public ResponseEntity<?> findId(@RequestParam String mail) {
    String id = accountService.findId(mail);
    String result = "등록된 메일 주소가 존재하지 않습니다";

    if (StringUtils.hasLength(id)) {
      MailDto mailDto = mailService.findId(mail, id);
      mailService.sendMail(mailDto);
      result = mail + "로 메일을 발송하였습니다";
    }

    return ResponseEntity.ok(result);
  }
  //비밀번호체크 api
  @PostMapping("/check-pw")
  public ResponseEntity<?> checkPassword(@RequestBody AccountModel accountModel, @AuthenticationPrincipal AccountModel user ) {

    accountModel.setUserId(user.getUserId());

  boolean result = accountService.checkPassword(accountModel);

  return ResponseEntity.ok(result);
  }

  //비밀번호변경
  @PostMapping("/change-pw")
  public ResponseEntity<?> changePassword(@RequestBody AccountDto request, @AuthenticationPrincipal AccountModel user) {

    user.setPassword(request.getPassword());
    AccountDto accountDto = user.toDto();
    accountService.changePassword(accountDto);

    return ResponseEntity.ok(accountDto);
  }

  //pw 찾기
  @GetMapping("/find-pw")
  public ResponseEntity<?> findPassword(@RequestParam String userid, String mail) {

    String result = accountService.findPassword(userid, mail);

    return ResponseEntity.ok(result);
  }
}
