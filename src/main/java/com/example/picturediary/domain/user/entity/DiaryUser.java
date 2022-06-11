package com.example.picturediary.domain.user.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "diary_user")
public class DiaryUser extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String socialId;

    @Builder
    private DiaryUser(Long userId, String socialId)
    {
        this.userId = userId;
        this.socialId = socialId;
    }
}
