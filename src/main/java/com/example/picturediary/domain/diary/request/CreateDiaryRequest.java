package com.example.picturediary.domain.diary.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "일기 생성 요청")
public class CreateDiaryRequest
{
    @ApiModelProperty(value = "날씨", example = "Sun|Cloud|Rain|Snow")
    private String weather;

    @ApiModelProperty(value = "그림 일기 내용")
    private String content;

    @ApiModelProperty(value = "그림 일기 이미지 url")
    private String imageUrl;
}
