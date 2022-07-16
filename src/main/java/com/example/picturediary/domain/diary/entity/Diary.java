package com.example.picturediary.domain.diary.entity;

import com.example.picturediary.common.entity.BaseTimeEntity;
import com.example.picturediary.common.enums.Weather;
import com.example.picturediary.domain.diary.request.CreateDiaryRequest;
import com.example.picturediary.domain.stamp.entity.Stamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "diary")
public class Diary extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long diaryId;

    private Long  userId;

    private String imageUrl;

    private Weather weather;

    @Lob
    private String content;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stamp> stampList;

    @Builder
    private Diary(
        Long diaryId,
        Long userId,
        String imageUrl,
        Weather weather,
        String content)
    {
        this.diaryId = diaryId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.weather = weather;
        this.content = content;
    }

    public static Diary of(
        CreateDiaryRequest createDiaryRequest,
        UserDetails user)
    {
        return Diary.builder()
            .userId(Long.parseLong(user.getUsername()))
            .imageUrl(createDiaryRequest.getImageUrl())
            .weather(Weather.valueOf(createDiaryRequest.getWeather()))
            .content(createDiaryRequest.getContent())
            .build();
    }
}
