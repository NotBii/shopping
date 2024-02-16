package com.zerobase.shopping.product.entity;

import com.zerobase.shopping.imgUpload.entity.ImgEntity;
import com.zerobase.shopping.product.dto.CreateProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  private LocalDateTime date;
  private LocalDateTime modifiedDate;
  @OneToMany(orphanRemoval = true)
  @JoinColumn(name = "productId")
  private List<ImgEntity> imgList;
  private int deleteYn;

  public void changeDeleteYn(int no) {
    this.deleteYn = no;
  }

}
