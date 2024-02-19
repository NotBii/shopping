package com.zerobase.shopping.inquiry.entity;

import com.zerobase.shopping.img.entity.ImgEntity;
import com.zerobase.shopping.inquiry.dto.WriteRequest;
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
import java.util.ArrayList;
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
  @JoinColumn(name = "imgs")
  @Default
  private List<ImgEntity> imgList = new ArrayList<>();

  @Default
  private int isDeleted = 0;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime modifiedDate;

  private long readCount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parentId")
  private InquiryEntity parent;

  @Builder.Default
  @OneToMany(mappedBy = "parent", orphanRemoval = true)
  private List<InquiryEntity> children = new ArrayList<>();

  private int isSecret;

  public void changeIsDeleted(int no) {this.isDeleted = no;}
  public void readCountUp() { this.readCount += 1; }


  @PrePersist
  public void onPrepersist() {
    this.createdDate = LocalDateTime.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }
  @PreUpdate
  public void onPreUpdate() {
    this.modifiedDate = LocalDateTime.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

  public void update(WriteRequest request, List<ImgEntity> imgs) {
    this.title = request.getTitle();
    this.content = request.getContent();
    this.imgList.clear();
    this.imgList.addAll(imgs);

  }




}
