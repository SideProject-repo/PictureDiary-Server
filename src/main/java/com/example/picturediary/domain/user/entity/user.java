package com.example.picturediary.domain.user.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "diary_user")
public class user extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Long socialId;
}
