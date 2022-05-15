package com.example.picturediary.domain.stamp.service;

import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.diary.repository.DiaryRepository;
import com.example.picturediary.domain.stamp.entity.Stamp;
import com.example.picturediary.domain.stamp.repository.StampRepository;
import com.example.picturediary.domain.stamp.request.AddStampRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
        Stamp stamp = Stamp.of(addStampRequest, user, diary);
        stampRepository.save(stamp);
    }
}
