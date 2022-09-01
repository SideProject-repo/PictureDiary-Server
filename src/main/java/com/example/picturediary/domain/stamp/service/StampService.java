package com.example.picturediary.domain.stamp.service;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.diary.repository.DiaryRepository;
import com.example.picturediary.domain.stamp.entity.Stamp;
import com.example.picturediary.domain.stamp.repository.StampRepository;
import com.example.picturediary.domain.stamp.request.AddStampRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Transactional
@Service
public class StampService
{
    private final StampRepository stampRepository;
    private final DiaryRepository diaryRepository;

    @Autowired
    public StampService(
        StampRepository stampRepository,
        DiaryRepository diaryRepository)
    {
        this.stampRepository = stampRepository;
        this.diaryRepository = diaryRepository;
    }

    public void addStamp(AddStampRequest addStampRequest, UserDetails user)
    {
        Diary diary = diaryRepository.getDiaryByDiaryId(addStampRequest.getDiaryId());

        if (ObjectUtils.isEmpty(diary))
            throw new CustomError(ErrorCodes.NOT_EXIST_DIARY_ID);

        Stamp stamp = Stamp.of(addStampRequest, user, diary);
        stampRepository.save(stamp);
    }
}
