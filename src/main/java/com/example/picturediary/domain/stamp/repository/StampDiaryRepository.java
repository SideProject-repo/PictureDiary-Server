package com.example.picturediary.domain.stamp.repository;

import com.example.picturediary.domain.stamp.entity.StampDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampDiaryRepository extends JpaRepository<StampDiary, Long>
{
}
