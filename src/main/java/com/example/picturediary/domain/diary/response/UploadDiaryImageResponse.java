package com.example.picturediary.domain.diary.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "일기 이미지 업로드 응답")
public class UploadDiaryImageResponse
{
    @ApiModelProperty(value = "일기 이미지 url")
    private String imageUrl;

    @Builder
    private UploadDiaryImageResponse(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
}
