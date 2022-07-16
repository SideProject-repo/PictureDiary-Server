package com.example.picturediary.domain.diary.controller;

import com.example.picturediary.common.response.CommonResponse;
import com.example.picturediary.domain.diary.request.CreateDiaryRequest;
import com.example.picturediary.domain.diary.response.SingleDiaryResponse;
import com.example.picturediary.domain.diary.response.SingleDiaryWithStampResponse;
import com.example.picturediary.domain.diary.response.UploadDiaryImageResponse;
import com.example.picturediary.domain.diary.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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
    public ResponseEntity<SingleDiaryResponse> createDiary(
        CreateDiaryRequest createDiaryRequest,
        @AuthenticationPrincipal @ApiIgnore UserDetails user)
    {
        SingleDiaryResponse response = diaryService.createDiary(createDiaryRequest, user);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @ApiOperation("일기 이미지 업로드")
    @PostMapping(value = "/image",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> uploadDiaryImage( @RequestPart("image") MultipartFile image)
    {
        UploadDiaryImageResponse response = diaryService.uploadDiaryImage(image);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @ApiOperation("내가 쓴 일기 list 조회")
    @GetMapping(value = "/list/me")
    public ResponseEntity<List<SingleDiaryResponse>> getMyDiaryList(
        @ApiParam(value = "마지막으로 가져온 이전 일기 id") @RequestParam Long lastDiaryId,
        @ApiParam(value = "사이즈") @RequestParam Long size,
        @AuthenticationPrincipal @ApiIgnore UserDetails user)
    {
        List<SingleDiaryResponse> myDiaryList = diaryService.getMyDiaryList(lastDiaryId, size, user);
        return new ResponseEntity(myDiaryList, HttpStatus.OK);
    }

    @ApiOperation("모든 일기 list 조회")
    @GetMapping(value = "/list")
    public ResponseEntity<List<SingleDiaryResponse>> getDiaryList(
        @ApiParam(value = "마지막으로 가져온 이전 일기 id") @RequestParam Long lastDiaryId,
        @ApiParam(value = "사이즈") @RequestParam Long size)
    {
        List<SingleDiaryResponse> diaryList = diaryService.getDiaryList(lastDiaryId, size);
        return new ResponseEntity(diaryList, HttpStatus.OK);
    }

    @ApiOperation("일기 단건 조회")
    @GetMapping(value = "/{diaryId}")
    public ResponseEntity<SingleDiaryWithStampResponse> getDiary(
        @ApiParam(value = "일기 id") @PathVariable("diaryId") Long diaryId)
    {
        SingleDiaryWithStampResponse diary = diaryService.getDiary(diaryId);
        return new ResponseEntity(diary, HttpStatus.OK);
    }

    @ApiOperation("랜덤 일기 단건 조회")
    @GetMapping(value = "/random")
    public ResponseEntity<SingleDiaryWithStampResponse> getRandomDiary(@AuthenticationPrincipal @ApiIgnore UserDetails user)
    {
        SingleDiaryWithStampResponse diary = diaryService.getRandomDiary(user);
        return new ResponseEntity(diary, HttpStatus.OK);
    }

    @ApiOperation("마지막 접속 이후 도장이 찍힌 새로운 일기 list 조회")
    @GetMapping(value = "/list/new-stamp")
    public ResponseEntity<List<SingleDiaryWithStampResponse>> getNewStampDiaryListAfterLastAccess(
        @AuthenticationPrincipal @ApiIgnore UserDetails user)
    {
        List<SingleDiaryWithStampResponse> newStampDiaryList = diaryService.getNewStampDiaryList(user);
        return new ResponseEntity(newStampDiaryList, HttpStatus.OK);
    }

    /**
     * 사용되는 도메인 로직상 user 패키지 안에 있는 것이 적합해보이지만 getNewStampDiaryListAfterLastAccess와 함께 호출되는 api이므로 이곳에 작성
     */
    @ApiOperation("사용장의 마지막 접속 시각 업데이트")
    @PostMapping(value = "/update/last-access-time")
    public ResponseEntity<CommonResponse> updateLastAccessDateTime(@AuthenticationPrincipal @ApiIgnore UserDetails user)
    {
        diaryService.updateLastAccessDateTime(user);
        return new ResponseEntity(
            new CommonResponse(user.getUsername() + "님의 마지막 접속시간 update 성공"), HttpStatus.OK
        );
    }
}
