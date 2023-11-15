package com.zerobase.shopping.service;

import com.zerobase.shopping.dao.ImgDao;
import com.zerobase.shopping.dto.ImgDto;
import com.zerobase.shopping.model.ImgUpdateModel;
import java.io.File;
import java.io.IOException;
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

    StringBuilder sb = new StringBuilder();
    for (ImgDto dto : imgs) {
      sb.append(dto.getNo() + ",");
    }
    sb.deleteCharAt(sb.length() - 1);
    fileNo = sb.toString();
    log.info("fileno" + fileNo);
    return fileNo;
  }

  @Transactional
  public String updateImg(final List<MultipartFile> multipartFiles, ImgUpdateModel imgUpdateModel) {
    StringBuilder sb = new StringBuilder(imgUpdateModel.getUpdatedImgs());

    if (imgUpdateModel.getAdd()==1) {
      List<ImgDto> imgs = uploadImgs(multipartFiles);
      String fileNo = saveImgs(imgs);
      sb.append(",");
      sb.append(fileNo);
    }
        String result = sb.toString();

    return result;
  }
}
