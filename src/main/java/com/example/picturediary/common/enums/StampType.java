package com.example.picturediary.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StampType
{
    GRAL("G-RAL을 해요"),
    DOTHIS("이걸 하네"),
    GOOD("참 잘했어요"),
    GREATJOB("수고했어요"),
    PERFECT("완벽해"),
    OH("오~!"),
    ZZUGUL("쭈굴"),
    HUNDRED("백점"),
    HOENG("호엥"),
    INTERESTING("흥미진진"),
    LOL("ㅋㅋㅋ"),
    ZZANG("짱!")
    ;

    private String value;

    public static final String ALLOWABLE_VALUES = "GRAL,DOTHIS,GOOD,GREATJOB,PERFECT,OH,ZZUGUL,HUNDRED" +
        ",HOENG,INTERESTING,LOL,ZZANG";
}
