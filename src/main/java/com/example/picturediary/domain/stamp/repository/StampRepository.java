package com.example.picturediary.domain.stamp.repository;

import com.example.picturediary.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, Long>
{
}
