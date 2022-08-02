package com.example.picturediary.domain.diary.repository.implement;

import com.example.picturediary.domain.diary.dto.DiaryWithStampListDto;
import com.example.picturediary.domain.diary.entity.Diary;
import com.example.picturediary.domain.diary.repository.DiaryRepositoryCustom;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.picturediary.domain.diary.entity.QDiary.diary;

public class DiaryRepositoryImpl implements DiaryRepositoryCustom
{
    private final JPAQueryFactory query;

    public DiaryRepositoryImpl(JPAQueryFactory query)
    {
        this.query = query;
    }

    @Override
    public DiaryWithStampListDto getRandomDiary(long userId)
    {
        Diary randomDiary = query.selectFrom(diary)
            .leftJoin(diary.stampList).fetchJoin()
            .where(diary.userId.ne(userId))
            .orderBy(Expressions.numberTemplate(Double.class, "function('DBMS_RANDOM.RANDOM')").asc())
            .limit(1)
            .fetchOne();

        return DiaryWithStampListDto.of(randomDiary);
    }
}
