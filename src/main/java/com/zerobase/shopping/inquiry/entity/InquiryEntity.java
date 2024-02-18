package com.zerobase.shopping.inquiry.entity;

import com.zerobase.shopping.img.entity.ImgEntity;
import com.zerobase.shopping.member.entity.MemberEntity;
import com.zerobase.shopping.product.entity.ProductEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Builder
@Getter
@Entity
@Table(name="inquiry")
@NoArgsConstructor
@AllArgsConstructor
public class InquiryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long inquiryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "memberId")
  private MemberEntity writer;

  private String title;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "productId")
  private ProductEntity productId;

  @OneToMany(orphanRemoval = true)
  @JoinColumn(name = "inquiryId")
  private List<ImgEntity> imgList;

  @Default
  private int deleteYn = 0;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime modifiedDate;

  private long readCount;

  public void changeDeleteYn(int no) {this.deleteYn = no;}

  @PrePersist
  public void onPrepersist() {
    this.createdDate = LocalDateTime.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
  }
  @PreUpdate
  public void onPreUpdate() {
    this.modifiedDate = LocalDateTime.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
  }



}