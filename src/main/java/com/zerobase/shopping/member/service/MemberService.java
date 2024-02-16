package com.zerobase.shopping.member.service;

import com.zerobase.shopping.commons.exception.impl.DuplicatedMail;
import com.zerobase.shopping.commons.exception.impl.DuplicatedNickname;
import com.zerobase.shopping.commons.exception.impl.DuplicatedUsername;
import com.zerobase.shopping.commons.exception.impl.MemberNotFound;
import com.zerobase.shopping.commons.exception.impl.PasswordNotMatches;
import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.member.dto.MemberResponse;
import com.zerobase.shopping.member.dto.SignInRequest;
import com.zerobase.shopping.member.dto.SignUpRequest;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.dao.AccountDao;
import com.zerobase.shopping.model.MemberModel;
import com.zerobase.shopping.member.repository.MemberRepository;
import com.zerobase.shopping.service.MailService;
import java.security.SecureRandom;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//TODO : 비밀번호 정규식, 로그아웃


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public MemberDetails loadUserByUsername(String username) {
    MemberEntity entity = getMemberEntity(username);


    log.info(username + "정보 로드");
    return MemberDetails.toDto(entity);
  }

  //회원가입
  public MemberResponse signup(SignUpRequest request) {

    request.setPassword((this.passwordEncoder.encode(request.getPassword())));
    MemberEntity entity = request.toEntity();
    memberRepository.save(entity);

    return MemberResponse.getMemberResponse(entity);
  }

  //로그인
  //TODO sout 정리하기
  @Transactional
  public MemberResponse authenticate(SignInRequest request) {

    MemberEntity user = memberRepository.findByUsername(request.getUsername())
        .orElseThrow(MemberNotFound::new);

    if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new PasswordNotMatches();
    }
    System.out.println(user.getUsername());
    return MemberResponse.getMemberResponse(user);
  }

  //로그아웃
//  @Transactional
//  public void signout(String token, String username) {
//    TokenProvider.
//  }
  //id 중복체크
  public void idCheck(String username) {
    Optional<MemberEntity> op = memberRepository.findByUsername(username);
    if (op.isPresent()) {
      throw new DuplicatedUsername();
    }

  }

  //mail 중복체크
  public void mailCheck(String mail) {
    Optional<MemberEntity> op = memberRepository.findByMail(mail);
    if (op.isPresent()) {
      throw new DuplicatedMail();
    }
  }

  //닉네임 중복체크

  public void nicknameCheck(String nickname) {
    Optional<MemberEntity> op = memberRepository.findByNickname(nickname);
    if (op.isPresent()) {
      throw new DuplicatedNickname();
    }
  }

  //회원정보 조회

  public MemberDetails memberDetails(String username) {
    MemberEntity entity = memberRepository.findByUsername(username)
        .orElseThrow(MemberNotFound::new);


    return MemberDetails.toDto(entity);
  }

  //비밀번호 체크
  public void checkPassword(SignInRequest request, String username) {
    MemberEntity entity = getMemberEntity(username);
    if (!this.passwordEncoder.matches(request.getPassword(), entity.getPassword()))
    {
      throw new PasswordNotMatches();
    }
  }


  //비밀번호 변경
  @Transactional
  public void changePassword(SignInRequest request, String username) {
    MemberEntity entity = getMemberEntity(username);
    String newPw = passwordEncoder.encode(request.getPassword());
    entity.changePw(newPw);
    memberRepository.save(entity);

    log.info("비밀번호 변경");
  }


  //회원정보 수정
  public MemberDetails updateProfile(MemberDetails memberDetails) {
    MemberEntity entity = getMemberEntity(memberDetails.getUsername());
    entity.updateProfile(memberDetails);
    memberRepository.save(entity);

    return MemberDetails.toDto(entity);
  }
  @Transactional
  //회원정보 삭제
  //TODO 주문 등 타 테이블 같이삭제하도록
  public void resign(SignInRequest request) {
    authenticate(request);
    memberRepository.deleteMemberEntityByUsername(request.getUsername());
    log.info(request.getUsername() + "탈퇴완료");
  }

  //메일주소로 userId찾기
  public String findId(String mail) {

    return memberRepository.findByMail(mail).orElseThrow(MemberNotFound::new).getUsername();
  }

  @Transactional
//  userId, 메일주소로 비밀번호 초기화
  public void findPassword(String username, String mail) {
    MemberEntity entity = memberRepository.findByUsernameAndMail(username, mail).orElseThrow(MemberNotFound::new);

    String tempPassword = getTempPassword();
    entity.changePw(tempPassword);
    MemberDetails member = MemberDetails.toDto(entity);

    mailService.findPassword(member, tempPassword);

    log.info("임시 비밀번호 발급" + username);

  }

  //랜덤비밀번호 로직

  public String getTempPassword() {
    char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    StringBuffer sb = new StringBuffer();
    SecureRandom sr = new SecureRandom();

    int idx = 0;
    int len = charSet.length;

    for (int i = 0; i < 10; i++) {
      idx = sr.nextInt(len);
      sb.append(charSet[idx]);
    }

    return sb.toString();
  }

  //memberentity 호출

  private MemberEntity getMemberEntity(String username) {
    return memberRepository.findByUsername(username).orElseThrow(MemberNotFound::new);
  }
}
