package com.zerobase.shopping.member.controller;

import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.dto.MailDto;
import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.member.dto.MemberResponse;
import com.zerobase.shopping.member.dto.SignInRequest;
import com.zerobase.shopping.member.dto.SignUpRequest;
import com.zerobase.shopping.security.TokenProvider;
import com.zerobase.shopping.member.service.MemberService;
import com.zerobase.shopping.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

public class MemberController {

  private final MemberService memberService;
  private final MailService mailService;
  private final TokenProvider tokenProvider;



  //회원가입
  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {

    MemberResponse result = this.memberService.signup(request);

    return ResponseEntity.ok(result);
  }

  //로그인
  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
    MemberResponse user = this.memberService.authenticate(request);
    String token = this.tokenProvider.generateToken(user.getUsername(), user.getRole());

    log.info(user.getUsername() + " login");
    return ResponseEntity.ok(token);
  }

  //로그아웃
//  @GetMapping("/logout")
//  public ResponseEntity<?> logout(@AuthenticationPrincipal MemberDto memberDto, HttpServletRequest request, HttpServletResponse response) {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    if (memberDto != null) {
//      String accessToken = jwtAuthenticationFilter.resolveTokenFromRequest(request);
//      memberService.signout(accessToken, memberDto.getUsername());
//    }
//    if (auth != null) {
//      System.out.println("lout");
//      new SecurityContextLogoutHandler().logout(request, response, auth);
//    }
//    System.out.println("no");
//
//    return ResponseEntity.ok("로그아웃되었습니다");
//
//  }

  //id 중복체크
  @GetMapping("/check-id")
  public ResponseEntity<?> idCheck(@RequestParam String username) {

    memberService.idCheck(username);

    return ResponseEntity.ok("사용가능한 아이디입니다");

  }

  //email 중복체크
  @GetMapping ("/check-mail")
  public ResponseEntity<?> mailCheck(@RequestParam String mail) {

    memberService.mailCheck(mail);

    return ResponseEntity.ok("사용가능한 메일주소입니다");
  }

  //닉네임 중복체크
  @GetMapping ("/check-nickname")
  public ResponseEntity<?> nicknameCheck(@RequestParam String nickname) {

    memberService.nicknameCheck(nickname);

    return ResponseEntity.ok("사용가능한 닉네임입니다");
  }



  //회원 정보 조회
  @GetMapping ("/member-details")
  public ResponseEntity<?> memberDetails(@RequestParam String username) {

    MemberDetails result = memberService.memberDetails(username);

    return ResponseEntity.ok(result);
  }

  //비밀번호체크(fh api
  @PostMapping("/check-pw")
  public ResponseEntity<?> checkPassword(@RequestBody SignInRequest request, @AuthenticationPrincipal MemberDetails user ) {

    memberService.checkPassword(request, user.getUsername());

    return ResponseEntity.ok("비밀번호가 일치합니다");
  }

  //비밀번호변경
  @PostMapping("/change-pw")
  public ResponseEntity<?> changePassword(@RequestBody SignInRequest request, @AuthenticationPrincipal MemberDetails user) {

    memberService.changePassword(request, user.getUsername());

    return ResponseEntity.ok("변경완료");
  }


  //회원 정보 수정
  @PostMapping("/update-profile")
  public ResponseEntity<?> updateProfile(@RequestBody MemberDetails memberDetails) {

    MemberDetails result = memberService.updateProfile(memberDetails);

    return ResponseEntity.ok(result);
  }

  //회원 탈퇴
  @DeleteMapping("/resign")
  public ResponseEntity<?> resign(@RequestBody SignInRequest request) {

    memberService.resign(request);

    return ResponseEntity.ok("탈퇴 완료");
  }
  //id 찾기
  @GetMapping("/find-id")
  public ResponseEntity<?> findId(@RequestParam String mail) {
    String id = memberService.findId(mail);
    String result = "등록된 메일 주소가 존재하지 않습니다";

    if (StringUtils.hasLength(id)) {
      MailDto mailDto = mailService.findId(mail, id);
      mailService.sendMail(mailDto);
      result = mail + "로 메일을 발송하였습니다";
    }

    return ResponseEntity.ok(result);
  }


  //pw 찾기
  @GetMapping("/find-pw")
  public ResponseEntity<?> findPassword(@RequestParam String username, String mail) {

    memberService.findPassword(username, mail);

    return ResponseEntity.ok("가입된 메일주소로 임시비밀번호가 발송되었습니다.");
  }
}
