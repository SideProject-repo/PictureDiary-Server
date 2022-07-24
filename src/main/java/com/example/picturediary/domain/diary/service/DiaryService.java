package com.example.picturediary.domain.diary.service;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.common.util.S3Util;
import com.example.picturediary.domain.diary.dto.DiaryWithStampListDto;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.diary.repository.DiaryRepository;
import com.example.picturediary.domain.diary.request.CreateDiaryRequest;
import com.example.picturediary.domain.diary.response.RandomSingleDiaryWithStampResponse;
import com.example.picturediary.domain.diary.response.SingleDiaryResponse;
import com.example.picturediary.domain.diary.response.SingleDiaryWithStampResponse;
import com.example.picturediary.domain.diary.response.UploadDiaryImageResponse;
import com.example.picturediary.domain.user.entity.DiaryUser;
import com.example.picturediary.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class DiaryService
{
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final S3Util s3Util;

    @Autowired
    public DiaryService(
        DiaryRepository diaryRepository,
        UserRepository userRepository,
        S3Util s3Util)
    {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
        this.s3Util = s3Util;
    }

    public SingleDiaryResponse createDiary(CreateDiaryRequest createDiaryRequest, UserDetails user)
    {
        Diary saved = diaryRepository.save(Diary.of(createDiaryRequest, user));
        return SingleDiaryResponse.of(saved);
    }

    public UploadDiaryImageResponse uploadDiaryImage(MultipartFile image)
    {
        String imageUrl = s3Util.fileUpload(image);

        return UploadDiaryImageResponse.builder()
            .imageUrl(imageUrl)
            .build();
    }

    public List<SingleDiaryResponse> getMyDiaryList(Long lastDiaryId, Long size, UserDetails user)
    {
        List<Diary> myDiaryList = diaryRepository.getDiaryByUserId(lastDiaryId, size, Long.parseLong(user.getUsername()));

        List<SingleDiaryResponse> myDiaryListResponse = myDiaryList.stream()
            .map(SingleDiaryResponse::of)
            .collect(Collectors.toList());

        return myDiaryListResponse;
    }

    public List<SingleDiaryResponse> getDiaryList(Long lastDiaryId, Long size)
    {
        List<Diary> diaryList = diaryRepository.getDiaryList(lastDiaryId, size);

        List<SingleDiaryResponse> diaryListResponse = diaryList.stream()
            .map(SingleDiaryResponse::of)
            .collect(Collectors.toList());

        return diaryListResponse;
    }

    public SingleDiaryWithStampResponse getDiary(Long diaryId)
    {
        Diary diary = diaryRepository.getDiaryByDiaryId(diaryId);

        if (ObjectUtils.isEmpty(diary))
            throw new CustomError(ErrorCodes.NOT_EXIST_DIARY_ID);

        return SingleDiaryWithStampResponse.of(diary);
    }

    public RandomSingleDiaryWithStampResponse getRandomDiary(UserDetails user)
    {
        DiaryWithStampListDto randomDiary = diaryRepository.getRandomDiary(Long.parseLong(user.getUsername()));

        return RandomSingleDiaryWithStampResponse.of(randomDiary, Long.parseLong(user.getUsername()));
    }

    public List<SingleDiaryWithStampResponse> getNewStampDiaryList(UserDetails user)
    {
        DiaryUser diaryUser = userRepository.getDiaryUserByUserId(Long.parseLong(user.getUsername()));

        List<Diary> diaryList = diaryRepository.getDiaryByDiaryIdAndStampCreatedAtBefore(
            diaryUser.getUserId(), diaryUser.getLastAccessDateTime());

        return diaryList.stream()
            .map(SingleDiaryWithStampResponse::of)
            .collect(Collectors.toList());
    }

    public void updateLastAccessDateTime(UserDetails user)
    {
        DiaryUser diaryUser = userRepository.getDiaryUserByUserId(Long.parseLong(user.getUsername()));
        diaryUser.updateLastAccessDateTime();
    }
}
