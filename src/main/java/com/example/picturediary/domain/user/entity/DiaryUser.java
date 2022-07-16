package com.example.picturediary.domain.user.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;
import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

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

    private LocalDateTime lastAccessDateTime;

    @Builder
    private DiaryUser(Long userId, String socialId, LocalDateTime lastAccessDateTime)
    {
        this.userId = userId;
        this.socialId = socialId;
        this.lastAccessDateTime = lastAccessDateTime;
    }

    public void updateLastAccessDateTime()
    {
        if (LocalDateTime.now().isBefore(this.lastAccessDateTime))
            throw new CustomError(ErrorCodes.UNVALID_LAST_ACCESS_DATE_TIME);

        this.lastAccessDateTime = LocalDateTime.now();
    }
}
