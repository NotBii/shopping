package com.zerobase.shopping.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CartDto {
  private int no;
  private LocalDateTime date;
  private String name;
  private int productNo;
  private int productCount;



}
