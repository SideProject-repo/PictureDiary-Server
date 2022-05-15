package com.example.picturediary.domain.user.repository;

import com.example.picturediary.domain.user.entity.DiaryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DiaryUser, Long>
{
    boolean existsBySocialId(Long socialId);

}
