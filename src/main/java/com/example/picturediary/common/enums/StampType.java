package com.example.picturediary.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StampType
{
    GRAL("G-RAL을 해요"),
    DOTHIS("이걸 하네"),
    GOOD("잘했어요"),
    GREATJOB("수고했어요");

    private String value;
}
