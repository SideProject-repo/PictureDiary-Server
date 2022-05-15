package com.example.picturediary.domain.stamp.response;

import com.example.picturediary.common.enums.StampType;
import com.example.picturediary.domain.stamp.entity.Stamp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "일기에 찍힌 도장 응답")
public class StampInDiaryResponse
{
    @ApiModelProperty(value = "일기에 찍한 도장 id")
    private Long stampId;

    @ApiModelProperty(value = "도장 타입")
    private StampType stampType;

    @ApiModelProperty(value = "도장 x 좌표")
    private double x;

    @ApiModelProperty(value = "도장 y 좌표")
    private double y;

    @Builder
    private StampInDiaryResponse(
        Long stampId,
        StampType stampType,
        double x,
        double y)
    {
        this.stampId = stampId;
        this.stampType = stampType;
        this.x = x;
        this.y = y;
    }

    public static StampInDiaryResponse of(Stamp stamp)
    {
        return StampInDiaryResponse.builder()
            .stampId(stamp.getStampId())
            .stampType(stamp.getStampType())
            .x(stamp.getX())
            .y(stamp.getY())
            .build();
    }
}
