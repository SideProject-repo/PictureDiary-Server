package com.example.picturediary.domain.stamp.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;
import com.example.picturediary.common.enums.StampType;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.stamp.request.AddStampRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

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
@NoArgsConstructor
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

    private Long userId;

    @Builder
    public Stamp(
        Long stampId,
        Diary diary,
        StampType stampType,
        double x,
        double y,
        Long userId)
    {
        this.stampId = stampId;
        this.diary = diary;
        this.stampType = stampType;
        this.x = x;
        this.y = y;
        this.userId = userId;
    }

    public static Stamp of(AddStampRequest addStampRequest, UserDetails user, Diary diary)
    {
        return Stamp.builder()
            .diary(diary)
            .stampType(StampType.valueOf(addStampRequest.getStampType()))
            .x(addStampRequest.getX())
            .y(addStampRequest.getY())
            .userId(Long.parseLong(user.getUsername()))
            .build();
    }

    public boolean isSameUserId(long userId)
    {
        if (this.userId.equals(userId))
            return true;
        return false;
    }
}
