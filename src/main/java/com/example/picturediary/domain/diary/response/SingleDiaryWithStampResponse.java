package com.example.picturediary.domain.diary.response;

import com.example.picturediary.common.enums.Weather;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.stamp.response.StampInDiaryResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ApiModel(value = "일기 단건 및 일기에 찍힌 도장 응답")
public class SingleDiaryWithStampResponse
{
    @ApiModelProperty(value = "일기 id")
    private Long diaryId;

    @ApiModelProperty(value = "일기 이미지 url")
    private String imageUrl;

    @ApiModelProperty(value = "날씨", example = "SUN|CLOUD|RAIN|SNOW")
    private Weather weather;

    @ApiModelProperty(value = "일기 생성 날짜")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "일기에 찍힌 도장 list")
    private List<StampInDiaryResponse> stampList;

    @ApiModelProperty(value = "그림 일기 내용")
    private String content;

    @Builder
    private SingleDiaryWithStampResponse(
        Long diaryId,
        String imageUrl,
        Weather weather,
        LocalDateTime createdDate,
        List<StampInDiaryResponse> stampList,
        String content)
    {
        this.diaryId = diaryId;
        this.imageUrl = imageUrl;
        this.weather = weather;
        this.createdDate = createdDate;
        this.stampList = stampList;
        this.content = content;
    }

    public static SingleDiaryWithStampResponse of(Diary diary)
    {
        return SingleDiaryWithStampResponse.builder()
            .diaryId(diary.getDiaryId())
            .imageUrl(diary.getImageUrl())
            .weather(diary.getWeather())
            .createdDate(diary.getCreatedDate())
            .stampList(
                diary.getStampList().stream()
                    .map(StampInDiaryResponse::of)
                    .collect(Collectors.toList())
            )
            .content(diary.getContent())
            .build();
    }
}
