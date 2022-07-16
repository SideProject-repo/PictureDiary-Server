package com.example.picturediary.domain.diary.repository;

import com.example.picturediary.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
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

    @Query(value =
        "SELECT * " +
            "FROM DIARY d " +
            "WHERE DIARY_ID > (:lastDiaryId) " +
            "ORDER BY CREATED_DATE DESC " +
            "FETCH FIRST (:size) ROWS ONLY", nativeQuery = true)
    List<Diary> getDiaryList(Long lastDiaryId, Long size);

    Diary getDiaryByDiaryId(Long diaryId);

    @Query(value =
        "SELECT * " +
            "FROM " +
                "( SELECT * " +
                "FROM DIARY d " +
                "WHERE USER_ID != (:userId) " +
                "ORDER BY DBMS_RANDOM.RANDOM) d " +
            "where rownum <= 1", nativeQuery = true)
    Diary getRandomDiary(long userId);

    @Query(value =
    "SELECT d FROM " +
        "Diary d " +
        "JOIN fetch d.stampList s " +
        "WHERE d.userId = (:userId) " +
        "AND s.createdDate > (:lastAccessTime)")
    List<Diary> getDiaryByDiaryIdAndStampCreatedAtBefore(long userId, LocalDateTime lastAccessTime);
}
