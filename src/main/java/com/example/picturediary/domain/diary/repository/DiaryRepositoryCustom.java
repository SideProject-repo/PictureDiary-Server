package com.example.picturediary.domain.diary.repository;

import com.example.picturediary.domain.diary.dto.DiaryWithStampListDto;

public interface DiaryRepositoryCustom
{
    DiaryWithStampListDto getRandomDiary(long userId);
}
