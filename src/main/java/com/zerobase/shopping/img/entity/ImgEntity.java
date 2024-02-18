package com.zerobase.shopping.img.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "img")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long imgId;
  private String originalName;
  private String savedName;
  private long size;
  private LocalDateTime createAt;


}
