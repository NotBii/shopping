package com.zerobase.shopping.service;

import com.zerobase.shopping.dao.ImgDao;
import com.zerobase.shopping.dto.ImgDto;
import com.zerobase.shopping.model.ImgUpdateModel;
import com.zerobase.shopping.model.ProductModel;
import com.zerobase.shopping.commons.Utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
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
  private final ImgDao imgDao;
  private final Utils utils;

  /**
   * 이미지리스트 저장
   * @param multipartFiles - 파일객체목록
   * @return imgDto
   */

  public List<ImgDto> uploadImgs(final List<MultipartFile> multipartFiles) {
    List<ImgDto> imgs = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFiles) {
      if (multipartFile.isEmpty()) {
        continue;
      }
      imgs.add(uploadImg(multipartFile));
    }
    return imgs;
  }

  /**
   * 개별 이미지 저장
   * @param multipartFile - 파일 객체
   * @return imgDto
   */
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
        .fileName(multipartFile.getOriginalFilename())
        .savedFileName(saveName)
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
    if (dir.exists() == false) {
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
  public String saveImgs(final List<ImgDto> imgs) {
    String fileNo = "";
    if (CollectionUtils.isEmpty(imgs)) {
      return fileNo;
    }
    imgDao.saveAll(imgs);
    List<String> list= new ArrayList<>();
    for (ImgDto dto : imgs) {
      list.add(String.valueOf(dto.getNo()));
    }
    fileNo = String.join(",", list);

    log.info("fileno" + fileNo);
    return fileNo;
  }

  /**
   * 이미지 리스트 수정
   * @param productModel 원글 정보
   * @param multipartFiles
   * @param imgUpdateModel 이미지 추가/삭제 여부
   * @return 수정된 이미지 번호 리스트
   */
  @Transactional
  public String updateImg(ProductModel productModel,
      final List<MultipartFile> multipartFiles, ImgUpdateModel imgUpdateModel) {

    StringBuilder sb = new StringBuilder(productModel.getImg());
    if (imgUpdateModel.getAdd()==1) {
      List<ImgDto> newImgs = uploadImgs(multipartFiles);
      String fileNo = saveImgs(newImgs);
      sb.append(",");
      sb.append(fileNo);
    }
        String result = sb.toString();

    return result;
  }

  /**
   * 이미지 조회
   * @param imgNo 이미지번호 목록 (리스트)
   * @return 이미지DTO
   */
  public List<ImgDto> findAllByNo(final String imgNo) {
    if (imgNo == null) {
      return Collections.emptyList();
    }
    List<Long> list = utils.stringToList(imgNo);
    return imgDao.findAllByNo(list);
  }

  /**
   * DB에서 이미지 정보 삭제
   * @param img 원글에 저장된 이미지번호 (문자열)
   * @param imgNo 삭제할 이미지번호 목록 (리스트)
   * @return 수정된 이미지번호(문자열)
   */
  @Transactional
  public String deleteAllByNo(String img, final List<Long> imgNo) {
    if (CollectionUtils.isEmpty(imgNo)) {
      return img;
    }
    List<ImgDto> dtoList = imgDao.findAllByNo(imgNo);
    deleteFiles(dtoList);
    imgDao.deleteAllByNo(imgNo);
    List<Long> list = utils.stringToList(img);
    for (long no : imgNo) {
      list.remove(no);
    }

    String result = list.toString().replaceAll("[\\[\\]]", "");
    return result;
  }

  private void deleteFiles(final List<ImgDto> imgs) {
    if (CollectionUtils.isEmpty(imgs)) {
      return;
    }
    for (ImgDto img : imgs) {
      String uploadedDate = img.getDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd"));
      deleteFile(uploadedDate, img.getSavedFileName());
    }
  }

  private void deleteFile(final String addpath, final String filename) {
    String filePath = Paths.get(UPLOADPATH, addpath, filename).toString();
    deleteFile(filePath);
  }

  private void deleteFile(final String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
      log.info(filePath + " 삭제 완료");
    }
  }
}
