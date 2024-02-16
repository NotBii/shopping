package com.zerobase.shopping.imgUpload.service;

import com.zerobase.shopping.commons.exception.impl.FileNotFound;
import com.zerobase.shopping.commons.exception.impl.NoResult;
import com.zerobase.shopping.imgUpload.dto.ImgDto;
import com.zerobase.shopping.imgUpload.dto.ImgListUpdate;
import com.zerobase.shopping.imgUpload.entity.ImgEntity;
import com.zerobase.shopping.imgUpload.repository.ImgRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor

public class ImgService {
  private final String UPLOADPATH = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
  private final ImgRepository imgRepository;


  /**
   * 이미지리스트 저장
   * @param multipartFiles - 파일객체목록
   * @return List<imgDto> - 이미지 dto 목록
   */
  @Transactional
  public List<ImgDto> uploadImgs(final List<MultipartFile> multipartFiles) {
    List<ImgDto> imgs = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFiles) {
      if (multipartFile.isEmpty()) {
        continue;
      }
      imgs.add(uploadImg(multipartFile));
    }

    return saveImgs(imgs);
  }

  /**
   * 개별 이미지 저장
   * @param multipartFile - 파일 객체
   * @return imgDto
   */
  @Transactional
  public ImgDto uploadImg(final MultipartFile multipartFile) {
    String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    String uploadPath = getUploadPath(today) + File.separator + saveName;
    File uploadFile = new File(uploadPath);

    try {
      multipartFile.transferTo(uploadFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return ImgDto.builder()
        .originalName(multipartFile.getOriginalFilename())
        .savedName(saveName)
        .createAt(LocalDateTime.now())
        .size(multipartFile.getSize())
        .build();
  }

  /**
   * 파일명생성
   * @param filename 원본 파일명
   * @return 저장할파일명
   */
  private String generateSaveFilename(final String filename) {
    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    String extension = StringUtils.getFilenameExtension(filename);
    return uuid + "." + extension;
  }

  /**
   * 업로드 경로
   * @return 업로드 경로
   */
  private String getUploadPath() {
    return makeDirectories(UPLOADPATH);
  }

  /**
   * 업로드 경로
   * @param addPath 추가경로
   * @return
   */

  private String getUploadPath(final String addPath) {
    return makeDirectories(UPLOADPATH + File.separator + addPath);
  }

  /**
   * 업로드경로 폴더 생성
   * @param path 업로드경로
   * @return 업로드경로
   */
  private String makeDirectories(final String path) {
    File dir = new File(path);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir.getPath();
  }

  /**
   * 이미지정보 저장
   * @param imgs imgDto리스트
   * @return fileNo - 저장된 번호 리턴
   */

  @Transactional
  public List<ImgDto> saveImgs(List<ImgDto> imgs) {

    if (CollectionUtils.isEmpty(imgs)) {
      return null;
    }
    List<ImgEntity> entities = ImgEntityConverter.dtoListToEntityList(imgs);

    imgRepository.saveAll(entities);

    for (ImgEntity entity: entities) {
      log.info(entity.getImgId() + " 업로드 완료");
    }

    return ImgEntityConverter.entityListToDtoList(entities);
  }

  @Transactional
  public List<ImgDto> updateImgList(ImgListUpdate request, List<MultipartFile> files) {

    if (CollectionUtils.isEmpty(request.getOriginalList())) {
      throw new NoResult();
    }

    List<Long> originalList = request.getOriginalList();

    if (!CollectionUtils.isEmpty(request.getRemoveList())) {
      List<Long> removeList = request.getRemoveList();
      deleteImgs(removeList);
      originalList.removeAll(removeList);
    }


    List<ImgDto> updatedList = new ArrayList<>();
    for (Long id : originalList) {
      updatedList.add(ImgEntityConverter.entityToDto(getImgEntity(id)));
    }

    if (!files.isEmpty()) {
       updatedList.addAll(uploadImgs(files));
    }

    return updatedList;

  }
  /**
   * 이미지 조회
   * @param imgNo 이미지번호 목록 (리스트)
   * @return 이미지DTO
   */
//  public List<ImgDto> findAllByNo(final String imgNo) {
//    if (imgNo == null) {
//      return Collections.emptyList();
//    }
//    List<Long> list = utils.stringToList(imgNo);
//    return imgDao.findAllByNo(list);
//  }

  /**
   * 선택된 이미지 삭제
   * @param imgList 삭제할 이미지id 목록 (리스트)
   */
  @Transactional
  public void deleteImgs(List<Long> imgList) {
    if (CollectionUtils.isEmpty(imgList)) {
      return;
    }
    for (Long imgId : imgList) {
      deleteOneImg(imgId);
    }
  }


  //파일삭제 메소드
  @Transactional
  public void deleteOneImg(Long imgId) {
    ImgEntity entity = getImgEntity(imgId);
    String filepath = Paths.get(UPLOADPATH, entity.getCreateAt().format(DateTimeFormatter.ofPattern("yyMMdd")), entity.getSavedName()).toString();
    imgRepository.delete(entity);
    deleteImgFile(filepath);
  }


  //저장소에서 파일삭제
  private void deleteImgFile(String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    } else {
      throw new FileNotFound();
    }
  }

  public List<ImgEntity> getImgEntityList(List<ImgDto> imgList) {
    List<ImgEntity> result = new ArrayList<>();
    for (ImgDto dto: imgList) {
      result.add(getImgEntity(dto.getImgId()));
    }
    return result;
  }

  private ImgEntity getImgEntity(Long imgId) {

    return imgRepository.findByImgId(imgId).orElseThrow(NoResult::new);
  }

}
