package com.example.picturediary.domain.diary.response;

import com.example.picturediary.common.enums.Weather;
import com.example.picturediary.domain.diary.dto.DiaryWithStampListDto;
import com.example.picturediary.domain.stamp.entity.Stamp;
import com.example.picturediary.domain.stamp.response.StampInDiaryResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "랜덤 일기 단건 응답")
public class RandomSingleDiaryWithStampResponse
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

    @ApiModelProperty(value = "사용자가 자신이 해당 일기에 도장을 찍었는지 여부, 찍었으면 - true / 안 찍었으면 - false")
    private Boolean isStamped;

    @Builder
    private RandomSingleDiaryWithStampResponse(
        Long diaryId,
        String imageUrl,
        Weather weather,
        LocalDateTime createdDate,
        List<StampInDiaryResponse> stampList,
        String content,
        Boolean isStamped)
    {
        this.diaryId = diaryId;
        this.imageUrl = imageUrl;
        this.weather = weather;
        this.createdDate = createdDate;
        this.stampList = stampList;
        this.content = content;
        this.isStamped = isStamped;
    }

    public static RandomSingleDiaryWithStampResponse of(DiaryWithStampListDto diary, long userId)
    {
        boolean isStamped = false;

        for (Stamp stamp : diary.getStampList())
        {
            if(stamp.isSameUserId(userId)) isStamped = true;
        }

        return RandomSingleDiaryWithStampResponse.builder()
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
            .isStamped(isStamped)
            .build();
    }
}
