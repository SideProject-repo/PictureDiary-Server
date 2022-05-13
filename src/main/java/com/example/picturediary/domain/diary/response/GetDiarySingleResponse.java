package com.example.picturediary.domain.diary.response;

import com.example.picturediary.common.enums.Weather;
import com.example.picturediary.domain.stamp.response.StampInDiaryResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ApiModel(value = "일기 단건 조회 시 응답")
public class GetDiarySingleResponse
{
    @ApiModelProperty(value = "일기 id")
    private Long diaryId;

    @ApiModelProperty(value = "일기 이미지 url")
    private String imageUrl;

    @ApiModelProperty(value = "날씨", example = "Sun|Cloud|Rain|Snow")
    private Weather weather;

    @ApiModelProperty(value = "일기 생성 날짜")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "일기에 찍힌 도장 list")
    private List<StampInDiaryResponse> stampList;

    @Builder
    private GetDiarySingleResponse(
        Long diaryId,
        String imageUrl,
        Weather weather,
        LocalDateTime createdDate,
        List<StampInDiaryResponse> stampList)
    {
        this.diaryId = diaryId;
        this.imageUrl = imageUrl;
        this.weather = weather;
        this.createdDate = createdDate;
        this.stampList = stampList;
    }
}
