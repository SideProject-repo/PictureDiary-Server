package com.example.picturediary.domain.stamp.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stamp_diary")
public class StampDiary extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stampDiaryId;

    private Long diaryId;

    private String stampType;

    private double x;

    private double y;
}
