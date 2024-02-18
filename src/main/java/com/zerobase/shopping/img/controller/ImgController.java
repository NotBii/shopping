package com.zerobase.shopping.img.controller;

import com.zerobase.shopping.img.dto.ImgDto;
import com.zerobase.shopping.img.dto.ImgListUpdate;
import com.zerobase.shopping.img.service.ImgService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/img")
@RequiredArgsConstructor
public class ImgController {

  private final ImgService imgService;

//  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @PostMapping("/upload")
  public ResponseEntity<?> uploadImg(@RequestPart("imgs") List<MultipartFile> files) {

    List<ImgDto> result = imgService.uploadImgs(files);

    return ResponseEntity.ok(result);
  }

  /**
   *
   * @param request(ImgListUpdate)
   *         List<Long> removeList : 삭제할 이미지 번호
   *         List<Long> originalList : 업로드 했던 이미지 번호
   * @param files : 새로 추가 할 multippartfile
   * @return
   */
  @PostMapping("/update")
  public ResponseEntity<?> updateImgList(@RequestPart ImgListUpdate request,
      @RequestPart List<MultipartFile> files) {

    List<ImgDto> result = imgService.updateImgList(request, files);

    return ResponseEntity.ok(result);
  }



}
