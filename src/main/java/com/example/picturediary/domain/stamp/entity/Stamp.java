package com.example.picturediary.domain.stamp.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;
import com.example.picturediary.common.enums.StampType;
import com.example.picturediary.domain.diary.entity.Diary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "stamp")
public class Stamp extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stampId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private StampType stampType;

    private double x;

    private double y;
}
