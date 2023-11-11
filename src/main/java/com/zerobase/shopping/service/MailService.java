package com.zerobase.shopping.service;

import com.zerobase.shopping.dto.AccountDto;
import com.zerobase.shopping.dto.MailDto;
import com.zerobase.shopping.model.MailModel;
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
    MailModel mailModel = new MailModel();
    mailModel.setAddress(mail);
    mailModel.setTitle("쇼핑몰 id 찾기 결과");
    mailModel.setMessage("쇼핑몰의 id는   " + userId + "    입니다");

    MailDto mailDto = mailModel.toDto();
    log.info("id찾기 메일전송");

    return mailDto;
  }

  //비밀번호 찾기
  public void findPassword(AccountDto accountDto, String tempPassword) {
    log.info("findpw dto" + String.valueOf(accountDto));
    MailModel mailModel = MailModel.builder()
        .address(accountDto.getMail())
        .title("임시비밀번호 발급 안내입니다")
        .message(accountDto.getUserId() + "님의 임시비밀번호는" + tempPassword + "입니다.")
        .build();

    MailDto mailDto = mailModel.toDto();

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
