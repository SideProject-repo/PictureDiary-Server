package com.example.picturediary.domain.stamp.request;

import com.example.picturediary.common.enums.StampType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "도장 찍기 요청")
public class AddStampRequest
{
    @ApiModelProperty(value = "일기 id")
    private Long diaryId;

    @ApiModelProperty(value = "도장 타입", allowableValues = StampType.ALLOWABLE_VALUES)
    private String stampType;

    @ApiModelProperty(value = "도장 x 좌표")
    private double x;

    @ApiModelProperty(value = "도장 y 좌표")
    private double y;
}
