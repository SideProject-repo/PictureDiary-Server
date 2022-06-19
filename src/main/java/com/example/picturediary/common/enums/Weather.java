package com.example.picturediary.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Weather
{
    SUN("Sun"),
    CLOUD("Cloud"),
    RAIN("Rain"),
    SNOW("Snow");

    private String value;

    public static final String ALLOWABLE_VALUES = "SUN,CLOUD,RAIN,SNOW";
}
