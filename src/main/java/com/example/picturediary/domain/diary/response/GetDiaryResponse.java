package com.example.picturediary.domain.diary.response;

import com.example.picturediary.domain.diary.entity.Diary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel(value = "일기 리스트 조회 시 개별 응답")
public class GetDiaryResponse
{
    @ApiModelProperty(value = "일기 id")
    private Long diaryId;

    @ApiModelProperty(value = "일기 이미지 url")
    private String imageUrl;

    @ApiModelProperty(value = "날씨")
    private String weather;

    @ApiModelProperty(value = "일기 생성 날짜")
    private LocalDateTime createdDate;

    @Builder
    private GetDiaryResponse(
        Long diaryId,
        String imageUrl,
        String weather,
        LocalDateTime createdDate)
    {
        this.diaryId = diaryId;
        this.imageUrl = imageUrl;
        this.weather = weather;
        this.createdDate = createdDate;
    }

    public static GetDiaryResponse of (Diary diary)
    {
        return GetDiaryResponse.builder()
            .diaryId(diary.getDiaryId())
            .imageUrl(diary.getImageUrl())
            .weather(diary.getWeather())
            .createdDate(diary.getCreatedDate())
            .build();
    }
}
