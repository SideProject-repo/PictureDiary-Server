package com.example.picturediary.domain.stamp.repository;

import com.example.picturediary.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long>
{
    List<Stamp> findAllByDiaryDiaryId(long diaryId);
}
