package com.zerobase.shopping.model;

import com.zerobase.shopping.dto.MailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailModel {

  private String address;
  private String title;
  private String message;

  public MailDto toDto() {
    return MailDto.builder()
        .address(this.address)
        .title(this.title)
        .message(this.message)
        .build();
  }

}