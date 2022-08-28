select 1 ;
create schema if not exists wsin_wmp_escrow_batch_dev;
use wsin_wmp_escrow_batch_dev;

create table wsin_wmp_escrow_batch_dev.tb_wmp_ctlg_rank
(
    ctlg_seq    bigint unsigned                     not null comment '카탈로그아이디'
        primary key,
    final_score double                              not null comment '인기도스코어',
    info        text                                null comment '랭킹정보',
    reg_dt      timestamp default CURRENT_TIMESTAMP not null comment '등록일시',
    upd_dt      timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '변경일시'
)
    comment '가격비교 카탈로그 인기도 스코어 테이블' charset = utf8;

create index idx_tb_wmp_ctlg_rank_1
    on wsin_wmp_escrow_batch_dev.tb_wmp_ctlg_rank (upd_dt);
