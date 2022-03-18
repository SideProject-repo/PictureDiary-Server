package com.example.picturediary.domain.diary.repository;

import com.example.picturediary.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long>
{
}
