package com.example.picturediary.domain.diary.request;

import com.example.picturediary.common.enums.Weather;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "일기 생성 요청")
public class CreateDiaryRequest
{
    @ApiModelProperty(value = "날씨", allowableValues = Weather.ALLOWABLE_VALUES)
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
