package com.example.picturediary.domain.diary.controller;

import com.example.picturediary.common.annotation.CurrentUser;
import com.example.picturediary.common.response.CommonResponse;
import com.example.picturediary.domain.diary.request.CreateDiaryRequest;
import com.example.picturediary.domain.diary.response.UploadDiaryImageResponse;
import com.example.picturediary.domain.diary.service.DiaryService;
import com.example.picturediary.domain.user.entity.DiaryUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/diary")
public class DiaryController
{
    private final DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService)
    {
        this.diaryService = diaryService;
    }

    @ApiOperation("일기 생성")
    @PostMapping
    public ResponseEntity<CommonResponse> createDiary(
        @RequestBody CreateDiaryRequest createDiaryRequest,
        @AuthenticationPrincipal @ApiIgnore UserDetails user)
    {
        diaryService.createDiary(createDiaryRequest, user.getUsername());
        return new ResponseEntity<>(new CommonResponse("일기 생성 성공"), HttpStatus.OK);
    }

    @ApiOperation("일기 이미지 업로드")
    @PostMapping(value = "/image",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> uploadDiaryImage( @RequestPart("image") MultipartFile image)
    {
        UploadDiaryImageResponse response = diaryService.uploadDiaryImage(image);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}