package com.example.picturediary.domain.diary.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;
import com.example.picturediary.domain.diary.request.CreateDiaryRequest;
import com.example.picturediary.domain.user.entity.DiaryUser;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "diary")
public class Diary extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long diaryId;

    private Long userId;

    private String imageUrl;

    private String weather;

    @Lob
    private String content;

    @Builder
    private Diary(
        Long diaryId,
        Long userId,
        String imageUrl,
        String weather,
        String content)
    {
        this.diaryId = diaryId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.weather = weather;
        this.content = content;
    }

    public static Diary of(
        CreateDiaryRequest createDiaryRequest,
        String userId)
    {
        return Diary.builder()
            .userId(Long.parseLong(userId))
            .imageUrl(createDiaryRequest.getImageUrl())
            .weather(createDiaryRequest.getWeather())
            .content(createDiaryRequest.getContent())
            .build();
    }
}
