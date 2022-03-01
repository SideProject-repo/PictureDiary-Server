package com.example.picturediary.domain.diary.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "diary")
public class Diary extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long diaryId;

    private Long userId;

    private String imageUrl;

    private String weather;

    @Lob
    private String content;
}
