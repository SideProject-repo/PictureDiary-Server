package com.example.picturediary.domain.diary.service;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.common.util.S3Util;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.diary.repository.DiaryRepository;
import com.example.picturediary.domain.diary.request.CreateDiaryRequest;
import com.example.picturediary.domain.diary.response.GetDiaryListResponse;
import com.example.picturediary.domain.diary.response.GetDiarySingleResponse;
import com.example.picturediary.domain.diary.response.UploadDiaryImageResponse;
import com.example.picturediary.domain.stamp.repository.StampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaryService
{
    private final DiaryRepository diaryRepository;
    private final StampRepository stampRepository;
    private final S3Util s3Util;

    @Autowired
    public DiaryService(
        DiaryRepository diaryRepository,
        StampRepository stampRepository,
        S3Util s3Util)
    {
        this.diaryRepository = diaryRepository;
        this.stampRepository = stampRepository;
        this.s3Util = s3Util;
    }

    public GetDiaryListResponse createDiary(CreateDiaryRequest createDiaryRequest, UserDetails user)
    {
        Diary saved = diaryRepository.save(Diary.of(createDiaryRequest, user));
        return GetDiaryListResponse.of(saved);
    }

    public UploadDiaryImageResponse uploadDiaryImage(MultipartFile image)
    {
        String imageUrl = s3Util.fileUpload(image);

        return UploadDiaryImageResponse.builder()
            .imageUrl(imageUrl)
            .build();
    }

    public List<GetDiaryListResponse> getMyDiaryList(Long lastDiaryId, Long size, UserDetails user)
    {
        List<Diary> myDiaryList = diaryRepository.getDiaryByUserId(lastDiaryId, size, Long.parseLong(user.getUsername()));

        List<GetDiaryListResponse> myDiaryListResponse = myDiaryList.stream()
            .map(GetDiaryListResponse::of)
            .collect(Collectors.toList());

        return myDiaryListResponse;
    }

    public List<GetDiaryListResponse> getDiaryList(Long lastDiaryId, Long size)
    {
        List<Diary> diaryList = diaryRepository.getDiaryList(lastDiaryId, size);

        List<GetDiaryListResponse> diaryListResponse = diaryList.stream()
            .map(GetDiaryListResponse::of)
            .collect(Collectors.toList());

        return diaryListResponse;
    }

    public GetDiarySingleResponse getDiary(Long diaryId)
    {
        Diary diary = diaryRepository.getDiaryByDiaryId(diaryId);

        if (ObjectUtils.isEmpty(diary))
            throw new CustomError(ErrorCodes.NOT_EXIST_DIARY_ID);

        return GetDiarySingleResponse.of(diary);
    }

    public GetDiarySingleResponse getRandomDiary()
    {
        Diary diary = diaryRepository.getRandomDiary();
        diary.setStampList(stampRepository.findAllByDiaryDiaryId(diary.getDiaryId()));

        return GetDiarySingleResponse.of(diary);
    }
}
