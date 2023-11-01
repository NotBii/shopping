package com.zerobase.shopping.service;

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

  public MailDto findId(String mail, String id) {
    MailModel mailModel = new MailModel();
    mailModel.setAddress(mail);
    mailModel.setTitle("쇼핑몰 id 찾기 결과");
    mailModel.setMessage("쇼핑몰의 id는   " + id + "    입니다");

    MailDto mailDto = mailModel.toDto();

    return mailDto;
  }

  public void sendMail(MailDto mailDto) {
    log.info(mailDto.getAddress());
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(FROM_ADDRESS);
    message.setTo(mailDto.getAddress());
    message.setSubject(mailDto.getTitle());
    message.setText(mailDto.getMessage());
    mailSender.send(message);

    log.info("id찾기 메일전송");
  }

}
