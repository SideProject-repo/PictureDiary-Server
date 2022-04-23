package com.example.picturediary.domain.diary.repository;

import com.example.picturediary.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long>
{
    @Query(value =
        "SELECT * " +
        "FROM DIARY d " +
        "WHERE USER_ID = (:userId) " +
        "AND DIARY_ID > (:lastDiaryId) " +
        "ORDER BY CREATED_DATE DESC " +
        "FETCH FIRST (:size) ROWS ONLY", nativeQuery = true)
    List<Diary> getDiaryByUserId(Long lastDiaryId, Long size, Long userId);
}
