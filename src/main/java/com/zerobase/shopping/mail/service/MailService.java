package com.zerobase.shopping.mail.service;

import com.zerobase.shopping.member.dto.MemberDetails;
import com.zerobase.shopping.mail.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
  private JavaMailSender mailSender;
  private static final String FROM_ADDRESS = "crostic123@gmail.com";

  //id 찾기
  public MailDto findId(String mail, String userId) {
    MailDto mailDto = new MailDto();
    mailDto.setAddress(mail);
    mailDto.setTitle("쇼핑몰 id 찾기 결과");
    mailDto.setMessage("쇼핑몰의 id는   " + userId + "    입니다");

    log.info("id찾기 메일전송");

    return mailDto;
  }

  //비밀번호 찾기
  public void findPassword(MemberDetails member, String tempPassword) {

    MailDto mailDto = MailDto.builder()
        .address(member.getMail())
        .title("임시비밀번호 발급 안내입니다")
        .message(member.getUsername() + "님의 임시비밀번호는" + tempPassword + "입니다.")
        .build();


    this.sendMail(mailDto);
    log.info("비밀번호 초기화 메일전송");

  }

  // 메일 전송
  public void sendMail(MailDto mailDto) {
    log.info(mailDto.getAddress());
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(FROM_ADDRESS);
    message.setTo(mailDto.getAddress());
    message.setSubject(mailDto.getTitle());
    message.setText(mailDto.getMessage());
    mailSender.send(message);

    log.info("메일전송");
  }

}
