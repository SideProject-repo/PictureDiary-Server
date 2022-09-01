package com.example.picturediary.domain.stamp.request;

import com.example.picturediary.common.enums.StampType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ApiModel(value = "도장 찍기 요청")
public class AddStampRequest
{
    @NotNull
    @ApiModelProperty(value = "일기 id")
    private Long diaryId;

    @NotNull
    @Pattern(regexp = StampType.REG_EXP)
    @ApiModelProperty(value = "도장 타입", allowableValues = StampType.ALLOWABLE_VALUES)
    private String stampType;

    @NotNull
    @ApiModelProperty(value = "도장 x 좌표")
    private double x;

    @NotNull
    @ApiModelProperty(value = "도장 y 좌표")
    private double y;
}
