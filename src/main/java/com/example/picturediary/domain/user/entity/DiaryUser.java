package com.example.picturediary.domain.user.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;
import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.example.picturediary.domain.user.request.SignUpRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private String socialId;

    @NotNull
    private LocalDateTime lastAccessDateTime;

    private String socialType;

    @Builder
    private DiaryUser(Long userId, String socialId, LocalDateTime lastAccessDateTime, String socialType)
    {
        this.userId = userId;
        this.socialId = socialId;
        this.lastAccessDateTime = lastAccessDateTime;
        this.socialType = socialType;
    }

    public static DiaryUser of(SignUpRequest signUpRequest, String socialId)
    {
        return DiaryUser.builder()
            .socialId(socialId)
            .lastAccessDateTime(LocalDateTime.now())
            .socialType(signUpRequest.getSocialType())
            .build();
    }

    public void updateLastAccessDateTime()
    {
        if (LocalDateTime.now().isBefore(this.lastAccessDateTime))
            throw new CustomError(ErrorCodes.UNVALID_LAST_ACCESS_DATE_TIME);

        this.lastAccessDateTime = LocalDateTime.now();
    }
}
