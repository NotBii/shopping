package com.zerobase.shopping.imgUpload.service;

import com.zerobase.shopping.imgUpload.dto.ImgDto;
import com.zerobase.shopping.imgUpload.entity.ImgEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ImgEntityConverter {

  /**
   * 엔티티를 dto로 변환
   * @param entity ImgEntity
   * @return ImgDto
   */
  public static ImgDto entityToDto(ImgEntity entity) {
    return ImgDto.builder()
        .imgId(entity.getImgId())
        .originalName(entity.getOriginalName())
        .savedName(entity.getSavedName())
        .size(entity.getSize())
        .createAt(entity.getCreateAt())
        .build();
  }

  /**
   * dto를 엔티티로 변환
   * @param dto ImgDto
   * @return ImgEntity
   */
  private static ImgEntity dtoToEntity(ImgDto dto) {
    return ImgEntity.builder()
        .imgId(dto.getImgId())
        .originalName(dto.getOriginalName())
        .savedName(dto.getSavedName())
        .createAt(dto.getCreateAt())
        .size(dto.getSize())
        .build();
  }

  /**
   * 엔티티 리스트를 dto 리스트로
   * @param imgs List<ImgEntity>
   * @return List<ImgDto> result
   */
  public static List<ImgDto> entityListToDtoList(List<ImgEntity> imgs) {
    if (imgs == null) {
      return null;
    }

    List<ImgDto> result = new ArrayList<>();

    for (ImgEntity entity : imgs) {
      result.add(entityToDto(entity));
    }

    return result;
  }

  /**
   * dto 리스트를 엔티티 리스트로
   * @param imgs List<ImgDto> imgs
   * @return List<ImgEntity>
   */
  public static List<ImgEntity> dtoListToEntityList(List<ImgDto> imgs) {
    if (imgs == null) {
      return null;
    }

    List <ImgEntity> result = new ArrayList<>();

    for (ImgDto dto : imgs) {
      result.add(dtoToEntity(dto));
    }

    return result;
  }

}
