package com.example.picturediary.domain.diary.dto;

import com.example.picturediary.common.enums.Weather;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.stamp.entity.Stamp;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DiaryWithStampListDto
{
    private Long diaryId;
    private String imageUrl;
    private Weather weather;
    private LocalDateTime createdDate;
    private List<Stamp> stampList;
    private String content;

    @Builder
    public DiaryWithStampListDto(
        Long diaryId,
        String imageUrl,
        Weather weather,
        LocalDateTime createdDate,
        List<Stamp> stampList,
        String content)
    {
        this.diaryId = diaryId;
        this.imageUrl = imageUrl;
        this.weather = weather;
        this.createdDate = createdDate;
        this.stampList = stampList;
        this.content = content;
    }

    public static DiaryWithStampListDto of(Diary diary)
    {
        return DiaryWithStampListDto.builder()
            .diaryId(diary.getDiaryId())
            .imageUrl(diary.getImageUrl())
            .weather(diary.getWeather())
            .createdDate(diary.getCreatedDate())
            .stampList(diary.getStampList())
            .content(diary.getContent())
            .build();
    }
}
