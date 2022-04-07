package com.example.picturediary.domain.diary.service;

import com.example.picturediary.common.util.S3Util;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.diary.repository.DiaryRepository;
import com.example.picturediary.domain.diary.request.CreateDiaryRequest;
import com.example.picturediary.domain.diary.response.UploadDiaryImageResponse;
import com.example.picturediary.domain.user.entity.DiaryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DiaryService
{
    private final DiaryRepository diaryRepository;
    private final S3Util s3Util;

    @Autowired
    public DiaryService(
        DiaryRepository diaryRepository,
        S3Util s3Util)
    {
        this.diaryRepository = diaryRepository;
        this.s3Util = s3Util;
    }

    public void createDiary(CreateDiaryRequest createDiaryRequest, String userId)
    {
        Diary diary = Diary.of(createDiaryRequest, userId);
        diaryRepository.save(diary);
    }

    public UploadDiaryImageResponse uploadDiaryImage(MultipartFile image)
    {
        String imageUrl = s3Util.fileUpload(image);

        return UploadDiaryImageResponse.builder()
            .imageUrl(imageUrl)
            .build();
    }
}
