package com.example.picturediary.domain.diary.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "일기 생성 요청")
public class CreateDiaryRequest
{
    @ApiModelProperty(value = "날씨", example = "SUN|CLOUD|RAIN|SNOW")
    private String weather;

    @ApiModelProperty(value = "그림 일기 내용")
    private String content;

    @ApiModelProperty(value = "그림 일기 이미지 url")
    private String imageUrl;

    @Builder
    private CreateDiaryRequest(
        String weather
        , String content
        , String imageUrl)
    {
        this.weather = weather;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
