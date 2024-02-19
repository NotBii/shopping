package com.zerobase.shopping.product.entity;

import com.zerobase.shopping.img.entity.ImgEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long productId;
  private long price;
  private String title;
  private String content;
  private int stock;
  @CreatedDate
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime modifiedDate;
  @OneToMany(orphanRemoval = true)
  @JoinColumn(name = "productId")
  private List<ImgEntity> imgList;
  @Default
  private int deleteYn = 0;
  @Default
  private long totalScore = 0;
  @Default
  private long reviewCount = 0;


  public void changeDeleteYn(int no) {
    this.deleteYn = no;
  }

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

  public void updateScore(long score, int review) {
    this.totalScore += score;
    this.reviewCount += review;
  }

  public void updateStock(int count) {
    this.stock += count;
  }
}
