select 1 ;
create schema if not exists wsin_dev;
use wsin_dev;
alter database wsin_dev default character set = utf8;

-- wsin_dev.buycdt_set
create table if not exists buycdt_set
(
    buycdt_set_seq bigint auto_increment comment '구매조건셋 순번'
        primary key,
    ctlg_seq bigint not null comment '카탈로그 순번',
    ctlg_nm varchar(70) null comment '카탈로그 명',
    dflt_yn char not null comment '디폴트 여부',
    naver_main_type char default 'A' not null comment '네이버 카탈로그 대표 타입 (A: auto, M: manual)'
)
    comment '구매조건셋';

ALTER TABLE buycdt_set ADD INDEX (ctlg_seq, dflt_yn);

-- wsin_dev.wmp_category
create table if not exists wmp_category
(
    cate_cd int(11) unsigned not null comment '카테고리 코드'
        primary key,
    cate_nm varchar(50) not null comment '카테고리 이름',
    lcate_cd int(11) unsigned not null comment '대분류 코드',
    lcate_nm varchar(50) not null comment '대분류 명',
    mcate_cd int null comment '중분류 코드',
    mcate_nm varchar(50) null comment '중분류 명',
    scate_cd int null comment '소분류 코드',
    scate_nm varchar(50) null comment '소분류 명',
    dcate_cd int null comment '세분류 코드',
    dcate_nm varchar(50) null comment '세분류 명',
    dsp_yn char not null comment '노출 여부',
    use_yn char not null comment '사용여부',
    cate_depth bigint not null comment '카테고리 계층',
    full_cate_nm varchar(209) null comment '풀 카테고리 명',
    reg_dt datetime null
)
    comment '위메프 카테고리';

ALTER TABLE wmp_category ADD INDEX (lcate_cd);

ALTER TABLE wmp_category ADD INDEX (mcate_cd);

ALTER TABLE wmp_category ADD INDEX (scate_cd);

ALTER TABLE wmp_category ADD INDEX (dcate_cd);

-- wsin_dev.cate_kwrd
create table if not exists cate_kwrd
(
    dcate_cd int(11) unsigned not null comment '세분류 코드'
        primary key,
    cate_kwrd varchar(2000) not null comment '카테고리 키워드',
    reg_id varchar(64) not null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id varchar(64) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '카테고리 키워드';

-- wsin_dev.wmp_excld_prod
create table if not exists wmp_excld_prod
(
    `wmp_prod_seq` bigint(20) NOT NULL COMMENT '배송2.0 상품 순번',
    `excld_rsn` varchar(255) DEFAULT NULL COMMENT '제외 이유',
    `reg_id` varchar(64) DEFAULT NULL COMMENT '등록자 ID',
    `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `chg_id` varchar(64) DEFAULT NULL COMMENT '수정자 ID',
    `chg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
        PRIMARY KEY (`wmp_prod_seq`)
) COMMENT='WMP 매칭제외 상품';

-- wsin_dev.ws_excld_prod
create table if not exists ws_excld_prod
(
    `excld_rsn` varchar(255) DEFAULT NULL COMMENT '제외 이유',
    `ws_prod_seq` char(32) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL COMMENT '원더쇼핑 상품 순번',
    `reg_id` varchar(64) DEFAULT NULL COMMENT '등록자 ID',
    `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `chg_id` varchar(64) DEFAULT NULL COMMENT '수정자 ID',
    `chg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`ws_prod_seq`)
) COMMENT='원더쇼핑 매칭제외 상품';


-- wsin_dev.user
create table if not exists user
(
    user_id varchar(64) not null comment '사용자 ID'
        primary key,
    user_nm varchar(50) null comment '사용자 이름',
    emp_id varchar(15) null comment '사번',
    dept_id int(32) null comment '부서 ID',
    ldept_id int null comment '대부서 ID',
    ldept_nm varchar(32) null comment '대부서 명',
    mdept_id int null comment '중부서 ID',
    mdept_nm varchar(32) null comment '중부서 명',
    sdept_id int null comment '소부서 ID',
    sdept_nm varchar(32) null comment '소부서 명',
    user_stts int null comment '사용자 상태',
    user_enable int default 1 not null comment '사용자 가능여부',
    user_dtl varchar(1000) null comment '사용자 상세',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록 일시',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정 일시',
    login_pw varchar(255) null comment 'WSIN CMS 로그인 password'
)
    comment '사용자 테이블';

-- wsin_dev.user_role
CREATE TABLE if not exists user_role
(
    `user_id` varchar(64) NOT NULL COMMENT '사용자 ID',
    `role_id` int(12) NOT NULL COMMENT '권한 ID',
    `chg_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 시각',
    PRIMARY KEY (`user_id`,`role_id`)
) COMMENT='사용자 권한 매핑 테이블';

-- wsin_dev.role
CREATE TABLE if not exists role
(
    `role_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '권한 ID',
    `role_nm` varchar(32) NOT NULL COMMENT '권한 이름',
    `wechat_work_tag_nm` varchar(32) DEFAULT NULL COMMENT '기업위챗 태그 이름',
    PRIMARY KEY (`role_id`)
) COMMENT='권한 테이블';

-- wsin_dev.dept
create table if not exists dept
(
    dept_id int(32) not null comment '부서 ID'
        primary key,
    dept_nm varchar(32) null comment '부서 이름',
    dept_nm_en varchar(32) null comment '부서 이름 영어',
    dept_parent_id int(32) null comment '상위 부서 아이디',
    dept_odr int null comment '상위 부서 내의 순서, 값이 클수록 앞으로 온다',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정 일시'
)
    comment '부서';

-- wsin_dev.jwt_auth_history
CREATE TABLE if not exists jwt_auth_history
(
    `user_id` varchar(64) NOT NULL COMMENT '사용자 ID',
    `login_dt` varchar(24) NOT NULL COMMENT '로그인 일시',
    `jwt_hash` varchar(64) DEFAULT NULL COMMENT 'JWT 해쉬값',
    `enable_yn` char(1) DEFAULT NULL COMMENT '가능 여부',
    `logout_dt` datetime DEFAULT NULL COMMENT '로그아웃 일시',
    `exchn_dt` datetime DEFAULT NULL COMMENT '교환 일시',
    `disable_dt` datetime DEFAULT NULL COMMENT '무효화 일시',
    `prev_jwt_hash` varchar(64) DEFAULT NULL COMMENT '이전 JWT 해쉬값',
    PRIMARY KEY (`user_id`,`login_dt`)
) COMMENT='히스토리';

-- ---------------------------------------------------------------------------------------------------------------------
-- 제조사 브랜드
-- ---------------------------------------------------------------------------------------------------------------------
-- wsin_dev.maker
create table if not exists maker
(
    maker_seq bigint auto_increment comment '제조사 순번'
        primary key,
    maker_nm varchar(50) not null comment '제조사 명',
    maker_eng_nm varchar(100) null,
    use_yn char default 'Y' not null comment '사용 여부',
    chg_enable_yn char default 'Y' not null comment '수정 가능 여부',
    reg_id varchar(64) not null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록 일시',
    chg_id varchar(64) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정 일시',
    constraint maker_nm_UNIQUE
        unique (maker_nm)
)
    comment '제조사';


-- wsin_dev.brand
create table if not exists brand
(
    brand_seq bigint auto_increment comment '브랜드 순번'
        primary key,
    maker_seq bigint not null comment '제조사 순번',
    brand_nm varchar(50) not null comment '브랜드 명',
    brand_eng_nm varchar(100) null,
    clst_yn char default 'N' not null comment '클러스터 여부',
    wstyle_yn char default 'N' not null comment 'W스타일 사용여부',
    wstyle_expl varchar(100) null comment 'W스타일 소개 타이틀',
    brand_prod_disp_yn char default 'Y' not null comment '브랜드 상품명 노출여부',
    site_url varchar(200) null comment '사이트 URL',
    use_yn char default 'Y' not null comment '배송2.0 브랜드 사용 여부',
    ctlg_dsp_yn char default 'Y' not null comment '브랜드 프론트 서비스 노출여부(카탈로그)',
    expl varchar(1000) null comment '설명',
    reg_id varchar(64) null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록 일시',
    chg_id varchar(64) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정 일시',
    constraint brand_nm_UNIQUE
        unique (brand_nm, maker_seq)
)
    comment '브랜드';

ALTER TABLE brand ADD INDEX (maker_seq);

-- wsin_dev.series
create table if not exists series
(
    series_seq bigint auto_increment comment '시리즈 순번'
        primary key,
    brand_seq bigint not null,
    series_nm varchar(50) not null comment '시리즈 명',
    series_eng_nm varchar(100) null,
    use_yn char default 'Y' not null,
    reg_id varchar(64) null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP not null,
    chg_id varchar(64) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null,
    constraint series_nm_UNIQUE
        unique (series_nm, brand_seq)
)
    comment '시리즈';

ALTER TABLE series ADD INDEX (brand_seq);


-- ---------------------------------------------------------------------------------------------------------------------
-- 카탈로그
-- ---------------------------------------------------------------------------------------------------------------------
-- wsin_dev.ctlg
create table if not exists ctlg
(
    ctlg_seq bigint auto_increment comment '카탈로그 순번'
        primary key,
    ctlg_nm varchar(70) not null comment '카탈로그 명',
    dcate_cd bigint not null comment '세분류 코드',
    maker_seq bigint null comment '제조사 순번',
    maker_nm varchar(50) null comment '제조사 명',
    brand_seq bigint null comment '브랜드 순번',
    brand_nm varchar(50) null comment '브랜드 명',
    series_seq bigint null comment '시리즈 순번',
    series_nm varchar(50) null comment '시리즈 명',
    model_nm varchar(50) null comment '모델 명',
    nrm_model_nm varchar(50) null comment '일반 모델 명',
    ctlg_naver_seq bigint null comment '카탈로그 네이버 순번',
    ctlg_enuri_seq bigint null comment '카탈로그 에누리 순번',
    ctlg_danawa_seq bigint null comment '카탈로그 다나와 순번',
    svc_yn char not null comment '서비스 여부',
    cmplt_yn char not null comment '완성 여부',
    crt_type varchar(10) not null comment '생성 타입',
    innr_kwrd varchar(200) null comment '내부 키워드',
    smr_attr varchar(2500) null comment '요약 속성',
    advtmt_words varchar(255) null comment '광고 문구',
    buycdt_use_yn char not null comment '구매조건 사용 여부',
    ctlg_memo varchar(1000) null comment '카탈로그 메모',
    reg_id varchar(64) null comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id varchar(64) null comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시',
    unit_price_use_yn char default 'N' not null comment '단가 사용여부',
    main_ctlg_yn      char default 'N' not null comment '주요 카탈로그 여부',
    oversea_yn      char default 'N' not null comment '해외 여부',
    ignore_ai_update_detail_yn char DEFAULT 'Y' NOT NULL COMMENT 'ai 수정 불가 여부',
    dsp_buycdt_nm_yn char(1) default 'N' comment '노출다른구성명 직접입력 여부',
    dsp_buycdt_nm varchar(50) comment '노출다른구성명'
)
    comment '카탈로그';

ALTER TABLE ctlg ADD INDEX (dcate_cd);
ALTER TABLE ctlg ADD INDEX (chg_dt);
ALTER TABLE ctlg ADD INDEX (nrm_model_nm);
ALTER TABLE ctlg ADD INDEX (svc_yn, cmplt_yn);
ALTER TABLE ctlg ADD INDEX (dcate_cd, ctlg_seq);

-- wsin_dev.ctlg_img
create table if not exists ctlg_img
(
    ctlg_img_seq bigint auto_increment comment '카탈로그 이미지 순번'
        primary key,
    ctlg_seq bigint not null comment '카탈로그 순번',
    img_type varchar(10) not null comment '상품 이미지 / 리스팅 이미지',
    dsp_odr smallint not null comment '노출 순서',
    img_url varchar(500) null comment '이미지 URL',
    wmp_prod_seq bigint null comment '위메프 상품 순번',
    img_src_type varchar(10) default 'MALL' not null comment '몰이미지 / 직접등록',
    img_size varchar(10) default 'ORIGIN' not null comment '이미지 사이즈',
    img_src_mall varchar(50) null comment '이미지 출처 몰',
    img_src_url varchar(500) null comment '이미지 출처 URL',
    rpst_img_yn varchar(1) default 'Y' not null comment '대표 이미지 여부',
    uuid varchar(100) null comment '이미지 식별자'
)
    comment '원부 이미지';

ALTER TABLE ctlg_img ADD INDEX (ctlg_seq, img_type, dsp_odr);


-- wsin_dev.ctlg_smr
create table if not exists ctlg_smr
(
    ctlg_seq bigint not null comment '카탈로그 순번'
        primary key,
    max_prc int default 0 null comment '최대 가격',
    min_prc int default 0 null comment '최소 가격',
    daily_min_prc int default 0 null comment '일간 최소 가격',
    lowest_price_chg_yn char default 'N' null comment '최저가변경 여부',
    map_cnt_all_sale int(12) default 0 null comment '매핑 카운트갯수 전체',
    map_cnt_in_sale int(12) default 0 null comment '매핑 카운트갯수 판매중',
    map_cnt_end_sale int(12) default 0 null comment '매핑 카운트갯수 판매종료',
    batch_seq bigint null comment '배치 순번',
    img_exist char default 'N' null comment '이미지 유무',
    reg_id varchar(64) not null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id varchar(64) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '카탈로그 요약';

ALTER TABLE ctlg_smr ADD INDEX (ctlg_seq);

-- wsin_dev.ctlg_others_id
create table if not exists ctlg_others_id
(
    ctlg_others_seq       bigint auto_increment comment '타사 카탈로그 테이블 순번'
        primary key,
    ctlg_seq              bigint                                not null comment '카탈로그 순번',
    buycdt_set_seq        bigint                                not null comment '구매조건셋 순번',
    other_ctlg_code       varchar(70)                           not null comment '타사 카탈로그 순번',
    other_ctlg_type       varchar(20)                           not null comment '타사 카탈로그 회사명',
    dsp_odr               int         default 0                 null comment '타사 카탈로그 순서',
    is_main               char        default 'N'               null comment '주요 카탈로그 여부 Y|N',
    reg_dt                datetime    default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id                varchar(64)                           null comment '수정 ID',
    chg_dt                datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '수정일시',
    ext_code              varchar(50) default '0'               not null comment '타사 카탈로그 속성 코드',
    lowest_price          int                                   not null comment '타사카탈로그 최저가',
    unique_hash           binary(32)                            null comment 'buycdt_set_seq + other_ctlg_code + ext_code + other_ctlg_type',
    lowest_price_chg_dt   datetime    default CURRENT_TIMESTAMP null comment '타사카탈로그 최저가 수정일시',
    is_auto               char        default 'Y'               not null comment '자동매핑 여부',
    is_valid_lowest_price char        default 'Y'               not null comment '타사카탈로그 최저가 유효 여부',
    is_manual_main        char        default 'N'               not null comment '수동대표 여부',
    is_auto_main          char        default 'N'               not null comment '자동대표 여부',
    constraint unique_hash_key
        unique (unique_hash)
)
    comment '카탈로그 타사 아이디';

create index buycdt_set_seq_and_other_ctlg_code_and_ext_code_index
    on wsin_dev.ctlg_others_id (buycdt_set_seq, other_ctlg_code, ext_code);

create index chg_dt_index
    on wsin_dev.ctlg_others_id (chg_dt);

create index ctlg_others_id_other_ctlg_code_IDX
    on wsin_dev.ctlg_others_id (other_ctlg_code, ext_code);

create index lowest_price_chg_dt_index
    on wsin_dev.ctlg_others_id (lowest_price_chg_dt);

create table if not exists ctlg_attr_map
(
    ctlg_attr_map_seq bigint auto_increment comment '원부 속성 매핑 순번'
        primary key,
    ctlg_seq bigint not null comment 'idx 추가',
    grp_attr_seq bigint null comment '그룹 속성 순번',
    attr_seq bigint not null comment '속성 순번',
    attr_elmt_seq bigint not null comment '속성값 순번',
    use_yn char default 'Y' not null comment '사용 여부'
)
    comment '원부 속성값 매핑';

ALTER TABLE ctlg_attr_map ADD INDEX (attr_seq);
ALTER TABLE ctlg_attr_map ADD INDEX (ctlg_seq);
ALTER TABLE ctlg_attr_map ADD INDEX (attr_elmt_seq);

create table if not exists theme_ctlg_exclude
(
    theme_seq bigint not null comment '테마 순번',
    ctlg_seq bigint not null comment '카탈로그 순번',
    primary key (theme_seq, ctlg_seq)
);


-- wsin_dev.ctlg_wmp_prod_map
create table if not exists ctlg_wmp_prod_map
(
    wmp_prod_seq bigint not null comment '위메프 상품 순번'
        primary key,
    buycdt_set_seq bigint not null comment '구매조건셋 순번',
    reg_id varchar(64) null comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id varchar(64) null comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시',
    valid_dt datetime default CURRENT_TIMESTAMP not null comment '유효성 일시',
    valid_yn char default 'Y' not null comment '유효성 여부'
)
    comment '카탈로그 배송2.0 상품 매핑';

ALTER TABLE ctlg_wmp_prod_map ADD INDEX (chg_dt);
ALTER TABLE ctlg_wmp_prod_map ADD INDEX (buycdt_set_seq);
ALTER TABLE ctlg_wmp_prod_map ADD INDEX (valid_yn, wmp_prod_seq);

-- wsin_dev.ctlg_ws_prod_map
create table if not exists ctlg_ws_prod_map
(
    ws_prod_seq char(32) not null -- collate latin1_bin
        primary key,
    buycdt_set_seq bigint not null comment '구매조건셋 순번',
    reg_id varchar(64) null comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id varchar(64) null comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시',
    valid_dt datetime default CURRENT_TIMESTAMP not null comment '유효성 일시',
    valid_yn char default 'N' null comment '유효성여부'
)
    comment '카탈로그 원더쇼핑 상품 매핑';

ALTER TABLE ctlg_ws_prod_map ADD INDEX (chg_dt);
ALTER TABLE ctlg_ws_prod_map ADD INDEX (buycdt_set_seq);
ALTER TABLE ctlg_ws_prod_map ADD INDEX (valid_yn, ws_prod_seq);

create table if not exists ctlg_lowest_price
(
    ctlg_seq bigint not null comment '카탈로그 순번'
        primary key,
    lowest_ctlg_versus_naver_yn char default 'N' not null comment '네이버 가격대비 최저가 카탈로그 여부',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_dt datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '수정일시',
    lowest_price bigint(20) default null,
    wmp_prod_seq bigint(20) default null,
    dcate_cd bigint(20) default null

)
    comment '네이버 비교 최저가 카탈로그';

ALTER TABLE ctlg_lowest_price ADD INDEX (chg_dt);

-- wsin_dev.buycdt
create table if not exists buycdt
(
    buycdt_seq bigint auto_increment comment '구매조건 순번'
        primary key,
    buycdt_nm varchar(50) null comment '구매조건 명',
    dsp_buycdt_nm varchar(50) not null comment '노출구매조건명',
    buycdt_type varchar(10) null comment '구매조건 형태',
    reg_id varchar(64) null comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null,
    chg_id varchar(64) null comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null,
    delete_yn char default 'N' not null,
    constraint buycdt_buycdt_nm_uindex
        unique (buycdt_nm)
)
    comment '구매조건';

ALTER TABLE buycdt ADD INDEX (buycdt_seq);

-- wsin_dev.buycdt_elmt
create table if not exists buycdt_elmt
(
    buycdt_elmt_seq bigint auto_increment comment '구매조건값 순번'
        primary key,
    buycdt_seq bigint not null comment '구매조건 순번',
    buycdt_elmt_nm varchar(50) not null comment '구매조건값 명',
    unt_nm varchar(20) null comment '단위 명',
    dsp_odr smallint default 0 not null comment '노출 순서',
    delete_yn char default 'N' not null comment '삭제 여부',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정 일시',
    unq_key varchar(100) not null comment '유니크 키 조합 (buycdt_seq | buycdt_elmt_nm | unt_nm)',
    constraint unq_key_UNIQUE
        unique (unq_key)
)
    comment '구매조건값';

ALTER TABLE buycdt_elmt ADD INDEX (buycdt_seq);
ALTER TABLE buycdt_elmt ADD INDEX (delete_yn);

-- wsin_dev.buycdt_elmt_set_map
create table if not exists buycdt_elmt_set_map
(
    buycdt_set_seq bigint not null comment '구매조건셋 순번',
    buycdt_elmt_seq bigint not null comment '구매조건값 순번',
    primary key (buycdt_set_seq, buycdt_elmt_seq)
)
    comment '구매조건값 구매조건셋 매핑';

ALTER TABLE buycdt_elmt_set_map ADD INDEX (buycdt_elmt_seq);

-- wsin_dev.buycdt_set_unit_price
create table if not exists buycdt_set_unit_price
(
    buycdt_set_seq bigint not null comment '구매조건 셋 순번'
        primary key,
    standard_volume int(12) null comment '기준 용량',
    standard_volume_unit_nm varchar(20) null comment '기준 용량 단위 명',
    prod_volume double null comment '제품 용량',
    prod_volume_unit_nm varchar(20) null comment '제품 용량 단위 명',
    quantity int(12) null comment '수량',
    quantity_unit_nm varchar(20) null comment '수량 단위 명',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일'
)
    comment '단가 구매조건';


-- wsin_dev.buycdt_unit_map

create table if not exists buycdt_unit_map
(
    buycdt_seq bigint not null comment '구매조건 순번',
    unt_nm varchar(20) not null comment '단위명 ',
    dsp_odr smallint default 0 null comment '단위 노출순서. ',
    primary key (buycdt_seq, unt_nm)
);

-- wsin_dev.cate_buycdt_map
create table if not exists cate_buycdt_map
(
    cate_buycdt_map_seq bigint auto_increment
        primary key,
    buycdt_seq bigint not null comment '구매조건 순번',
    cate_cd int(12) null comment '위메프 물리카테고리 코드',
    dsp_odr smallint not null comment '카테고리 구매조건 노출순서.',
    reg_id varchar(64) null comment '등록자',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일',
    chg_id varchar(64) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일',
    constraint cate_buycdt_map_buycdt_seq_cate_cd_uindex
        unique (buycdt_seq, cate_cd)
)
    comment '카테고리 구매조건 매핑';

ALTER TABLE cate_buycdt_map ADD INDEX (buycdt_seq);
ALTER TABLE cate_buycdt_map ADD INDEX (cate_cd);
ALTER TABLE cate_buycdt_map ADD INDEX (buycdt_seq);

create table if not exists buycdt_set_lowest_price
(
    buycdt_set_seq bigint not null comment '다른구성셋 순번'
        primary key,
    ctlg_seq bigint not null comment '카탈로그 순번',
    lowest_price int default 0 null comment '최저 가격',
    lowest_buycdt_set_versus_naver_yn char default 'N' not null comment '네이버 가격 대비 최저가 다른구성셋 여부',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_dt datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '수정일시',
    constraint ctlg_buycdt_set_seq_unique_key
        unique (ctlg_seq, buycdt_set_seq)
)
    comment '최저가 다른구성셋';

ALTER TABLE buycdt_set_lowest_price ADD INDEX (chg_dt);

-- wsin_dev.buycdt_set_smr
create table if not exists buycdt_set_smr
(
    ctlg_seq         bigint                             not null comment '카탈로그 순번',
    buycdt_set_seq   bigint                             not null comment '구매조건셋 순번',
    max_prc          int      default 0                 null comment '최대 가격',
    min_prc          int      default 0                 null comment '최소 가격',
    map_cnt_all_sale int(12)  default 0                 null comment '매핑 카운트갯수 전체',
    map_cnt_in_sale  int(12)  default 0                 null comment '매핑 카운트갯수 판매중',
    map_cnt_end_sale int(12)  default 0                 null comment '매핑 카운트갯수 판매종료',
    batch_seq        bigint                             null comment '배치 순번',
    reg_id           varchar(64)                        not null comment '등록자',
    reg_dt           datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id           varchar(64)                        null comment '수정자',
    chg_dt           datetime default CURRENT_TIMESTAMP not null comment '수정일시',
    primary key (ctlg_seq, buycdt_set_seq)
)
    comment '구매조건 요약';

create index idx_buycdt_set_seq
    on buycdt_set_smr (buycdt_set_seq);


-- ---------------------------------------------------------------------------------------------------------------------
-- 속성
-- ---------------------------------------------------------------------------------------------------------------------
-- wsin_dev.attr
create table if not exists attr
(
    attr_seq bigint auto_increment comment '속성 순번'
        primary key,
    mng_attr_nm varchar(50) null comment '관리 속성 명',
    dsp_attr_nm varchar(50) null comment '노출 속성 명',
    attr_ipt_type varchar(10) null comment '속성 입력 유형',
    attr_slt_type varchar(10) null comment '속성 선택 유형',
    delete_yn char default 'N' null comment '속성 삭제 여부',
    show_unit_map_yn char default 'N' not null comment '단위 노출 여부',
    reg_id varchar(64) null comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id varchar(64) null comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시',
    constraint attr_mng_attr_nm_uindex
        unique (mng_attr_nm)
)
    comment '속성';

ALTER TABLE attr ADD INDEX (delete_yn, attr_ipt_type);

-- wsin_dev.attr_elmt
create table if not exists attr_elmt
(
    attr_elmt_seq bigint auto_increment comment '속성값 순번'
        primary key,
    attr_seq bigint not null comment '속성 순번',
    attr_elmt_nm varchar(500) not null comment '속성값 이름',
    unt_nm varchar(20) null comment '단위명',
    dsp_odr smallint default 0 not null comment '노출 순서',
    parent_attr_elmt_seq bigint null comment '부모 속성값 순번',
    is_parent_yn char default 'Y' not null comment '부모 여부',
    delete_yn char default 'N' not null,
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정 일시',
    unq_key varchar(600) not null comment '유니크 키 조합 (attr_seq | attr_elmt_nm | unt_nm | parent_attr_elmt_seq)',
    constraint unique_key_UNIQUE
        unique (unq_key)
)
    comment '속성값';

ALTER TABLE attr_elmt ADD INDEX (attr_seq);
ALTER TABLE attr_elmt ADD INDEX (parent_attr_elmt_seq);

-- wsin_dev.group_attr
create table if not exists group_attr
(
    grp_attr_seq bigint auto_increment comment '그룹 속성 순번'
        primary key,
    dsp_odr smallint null comment '노출 순서',
    grp_attr_nm varchar(50) null comment '그룹 속성 명',
    cate_cd int(12) null,
    reg_id varchar(64) null comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null,
    chg_id varchar(64) null comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null,
    constraint grp_nm
        unique (grp_attr_nm, cate_cd)
)
    comment '그룹속성';


-- wsin_dev.cate_attr_map
create table if not exists cate_attr_map
(
    cate_attr_map_seq bigint auto_increment comment '카테고리 속성 매핑 순번'
        primary key,
    grp_attr_seq bigint null comment '그룹속성 순번',
    attr_seq bigint null comment '속성 순번',
    cate_cd int(12) null comment '카테고리 코드',
    require_attr_yn char default 'Y' not null comment '필수 속성 여부',
    dsp_odr smallint null comment '노출순서',
    reg_id varchar(64) null comment '수정자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP null,
    chg_id varchar(64) null comment '수정 ID',
    chg_dt datetime default CURRENT_TIMESTAMP null,
    listing_dsp_odr smallint null comment '리스팅 속성 순서',
    listing_dsp_yn char default 'Y' null comment '리스팅 속성 노출 유무',
    detail_dsp_yn char default 'Y' null comment '상세페이지 노출 여부',
    mobile_listing_dsp_yn char default 'Y' null comment '모바일 리스팅 노출 여부',
    constraint cate_attr_map_attr_seq_cate_cd_uindex
        unique (attr_seq, cate_cd)
)
    comment '카테고리 속성 매핑 테이블';

ALTER TABLE cate_attr_map ADD INDEX (cate_cd);
ALTER TABLE cate_attr_map ADD INDEX (attr_seq);
ALTER TABLE cate_attr_map ADD INDEX (chg_id);

-- wsin_dev.attr_unit_map
create table if not exists attr_unit_map
(
    unt_nm varchar(20) not null comment '단위 명',
    attr_seq bigint not null comment '속성 순번',
    dsp_odr smallint null comment '카탈로그 속성의 단위 선택목록에서 노출 순서',
    primary key (unt_nm, attr_seq)
)
    comment '속성 단위 매핑';

ALTER TABLE attr_unit_map ADD INDEX (attr_seq);

-- wsin_dev.group_attr
create table if not exists group_attr
(
    grp_attr_seq bigint auto_increment comment '그룹 속성 순번'
        primary key,
    dsp_odr smallint null comment '노출 순서',
    grp_attr_nm varchar(50) null comment '그룹 속성 명',
    cate_cd int(12) null,
    reg_id varchar(64) null comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null,
    chg_id varchar(64) null comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null,
    constraint grp_nm
        unique (grp_attr_nm, cate_cd)
)
    comment '그룹속성';


-- ---------------------------------------------------------------------------------------------------------------------
-- 추천상품
-- ---------------------------------------------------------------------------------------------------------------------
-- wsin_dev.VAI_GROUPS

create table if not exists vai_groups
(
    seq bigint(32) unsigned auto_increment comment 'seq',
    version tinyint default 0 not null comment '버전',
    group_key varchar(32) collate latin1_bin not null comment 'GROUP_KEY (레벨1)',
    parent_key varchar(32) collate latin1_bin not null comment 'PARENT_KEY (레벨2: GROUP_KEY의 상위키)',
    container varchar(20) collate latin1_bin null comment '그룹의 컨테이너명',
    partner_id varchar(20) null comment '파트너ID',
    product_count int default 0 null comment '하위상품개수 (parent_key일때:모든하위상품수, group_key일때:하위상품수)',
    sub_product_count int default 0 null comment '다른 하위 그룹의 상품개수 (parent_key일때: 하위그룹의 상품수, group_key일때:0)',
    title varchar(255) null comment '상품명',
    raw_token_title varchar(255) null comment '정제된 상품명',
    token_title varchar(200) null comment '추출된 상품명 토큰',
    token_model_no varchar(200) null comment '추출된 모델명 토큰',
    token_weight varchar(50) null comment '추출된 중량 토큰',
    token_type tinyint unsigned default 0 null comment '토큰유형',
    words text null comment '단어정보(현재미사용)',
    change_time datetime default CURRENT_TIMESTAMP not null comment '상품정보 수정일시',
    brand_no bigint(10) unsigned default 0 null comment '브랜드번호',
    primary key (seq),
    constraint group_key_version_UNIQUE unique (group_key, version)
)
    comment '그룹정보';

ALTER TABLE vai_groups ADD INDEX (seq);
ALTER TABLE vai_groups ADD INDEX (container, parent_key, group_key, product_count, partner_id);

-- wsin_dev.buycdt_set_lowest_price
CREATE TABLE IF NOT EXISTS buycdt_set_lowest_price
(
    buycdt_set_seq bigint NOT NULL COMMENT '다른구성셋 순번' PRIMARY KEY,
    ctlg_seq bigint NOT NULL COMMENT '카탈로그 순번',
    lowest_price int DEFAULT 0 NULL COMMENT '최저 가격',
    lowest_buycdt_set_versus_naver_yn char DEFAULT 'N' NOT NULL COMMENT '네이버 가격 대비 최저가 다른구성셋 여부',
    reg_dt datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '등록일시',
    chg_dt datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    CONSTRAINT ctlg_buycdt_set_seq_unique_key UNIQUE (ctlg_seq, buycdt_set_seq)
)
    COMMENT '최저가 다른구성셋';
ALTER TABLE buycdt_set_lowest_price ADD INDEX (chg_dt);

-- wsin_dev.ctlg_lowest_price
CREATE TABLE IF NOT EXISTS ctlg_lowest_price
(
    ctlg_seq bigint NOT NULL COMMENT '카탈로그 순번' PRIMARY KEY,
    lowest_ctlg_versus_naver_yn char DEFAULT 'N' NOT NULL COMMENT '네이버 가격대비 최저가 카탈로그 여부',
    reg_dt datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '등록일시',
    chg_dt datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
)
    COMMENT '네이버 비교 최저가 카탈로그';
ALTER TABLE ctlg_lowest_price ADD INDEX (chg_dt);

-- wsin_dev.inspt_ctlg
CREATE TABLE IF NOT EXISTS inspt_ctlg
(
    ctlg_seq                     bigint                             NOT NULL COMMENT '카탈로그 순번'
        primary key,
    lcate_cd                     int(12)                            NULL COMMENT '대분류 코드',
    mcate_cd                     int(12)                            NULL COMMENT '중분류 코드',
    scate_cd                     int(12)                            NULL COMMENT '소분류 코드',
    dcate_cd                     int(12)                            NULL COMMENT '세분류 코드',
    map_cnt_in_sale              int(12)  default 0                 NULL COMMENT '매핑카운트갯수-판매중',
    lowest_price_chg_yn          char                               NULL COMMENT '최저가 변경 여부',
    lowest_price_prodinfo_chg_yn char                               NULL COMMENT '최저가 상품정보 변경 여부',
    inspt_fns_yn                 char     default 'N'               NULL COMMENT '검수 완료 여부',
    inspt_fns_dt                 datetime                           NULL COMMENT '검수 완료 일시',
    prev_inspt_fns_dt            datetime                           NULL COMMENT '최종 검수일',
    reg_id                       varchar(64)                        NOT NULL COMMENT '등록자',
    reg_dt                       datetime default CURRENT_TIMESTAMP NOT NULL COMMENT '등록일시'
)
    comment '검수대상 카탈로그';

CREATE INDEX IX_dcate_cd
    ON wsin_dev.inspt_ctlg (dcate_cd);

CREATE INDEX IX_map_cnt_in_sale
    ON wsin_dev.inspt_ctlg (map_cnt_in_sale);

CREATE INDEX IX_mcate_cd
    ON wsin_dev.inspt_ctlg (mcate_cd);

CREATE INDEX IX_scate_cd
    ON wsin_dev.inspt_ctlg (scate_cd);


-- wsin_dev.inspt_done
create table if not exists inspt_done
(
    inspt_log_seq         bigint auto_increment comment '검수로그 순번'
        primary key,
    ctlg_seq              bigint      not null comment '카탈로그 순번',
    ppr_ctlg_yn           char        null comment '인기 카탈로그 여부',
    new_ctlg_yn           char        null comment '신규 카탈로그 여부',
    ppr_kwrd_ctlg_yn      char        null comment '인기검색어 카탈로그 여부',
    sp_ctlg_yn            char        null comment '검색특가 카탈로그 여부',
    ppr_inspt_fns_yn      char        null comment '인기 검수 완료 여부',
    new_inspt_fns_yn      char        null comment '신규 검수 완료 여부',
    ppr_kwrd_inspt_fns_yn char        null comment '인기검색어 검수 완료 여부',
    sp_inspt_fns_yn       char        null comment '검색특가 검수 완료 여부',
    inspt_fns_dt          datetime    null comment '검수 완료 일시',
    reg_id                varchar(64) not null comment '등록자'
)
    comment '카탈로그 검수로그';

create index IX_ctlg_seq
    on inspt_done (ctlg_seq);

create index IX_new_ctlg_yn
    on inspt_done (new_ctlg_yn);

create index IX_ppr_ctlg_yn
    on inspt_done (ppr_ctlg_yn);

create index IX_ppr_kwrd_ctlg_yn
    on inspt_done (ppr_kwrd_ctlg_yn);

create index IX_sp_ctlg_yn
    on inspt_done (sp_ctlg_yn);

-- wsin_dev.inspt_sp_ctlg
create table if not exists inspt_sp_ctlg
(
    ctlg_seq bigint                             not null comment '카탈로그 순번'
        primary key,
    dcate_cd int(12)                            null comment '세분류 코드',
    reg_id   varchar(64)                        not null comment '등록자',
    reg_dt   datetime default CURRENT_TIMESTAMP not null comment '등록일시'
)
    comment '검수대상 특가 카탈로그';

-- wsin_dev.ppr_kwrd_ctlg_mapp
create table if not exists ppr_kwrd_ctlg_mapp
(
    ppr_kwrd_seq bigint                             not null comment '인기검색어 순번',
    ctlg_seq     bigint                             not null comment '카탈로그 순번',
    reg_id       varchar(64)                        not null comment '등록자',
    reg_dt       datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    primary key (ppr_kwrd_seq, ctlg_seq)
)
    comment '인기검색어 카탈로그 매핑';

-- wsin_dev.mtrstat_by_period
CREATE TABLE IF NOT EXISTS wsin_dev.mtrstat_by_period
(
    anlys_dt                    varchar(10)  not null comment '통계 일시'
        primary key,
    batch_seq                   bigint       null comment '배치 순번',
    ctlg_all_cnt                int(12)      null comment '카탈로그 전체 갯수',
    ctlg_inspt_fns_cnt          int(12)      null comment '카탈로그 검수완료 갯수',
    ctlg_chg_cnt                int(12)      null comment '카탈로그 변경 갯수',
    ctlg_chg_ratio              float(12, 1) null comment '카탈로그 수정율',
    mtch_prod_all_cnt           int(12)      null comment '매칭상품 전체 갯수',
    prod_sp_cnt                 int(12)      null comment '상품 검색특가 갯수',
    prod_inspt_fns_cnt          int(12)      null comment '상품 검수완료 갯수',
    prod_sp_inspt_fns_cnt       int(12)      null comment '상품 검색특가 검수완료 갯수',
    prod_chg_all_cnt            int(12)      null comment '상품 수정 전체 갯수',
    prod_chg_lowest_price_cnt   int(12)      null comment '상품 수정 최저가 갯수',
    prod_chg_sp_cnt             int(12)      null comment '상품 수정 검색특가 갯수',
    prod_chg_ratio              float(12, 1) null comment '상품 수정율',
    prod_lowest_price_chg_ratio float(12, 1) null comment '상품 최저가 수정율',
    prod_sp_chg_ratio           float(12, 1) null comment '상품 검색특가 수정율',
    reg_dt                      datetime     null comment '등록일시',
    reg_id                      varchar(64)  not null comment '등록자'
)
    comment '모니터링 기간별 통계';

-- wsin_dev.mtrstat_by_cate
CREATE TABLE IF NOT EXISTS wsin_dev.mtrstat_by_cate
(
    anlys_dt                    varchar(10)  not null comment '통계 일시',
    anlys_type                  varchar(10)  not null comment '통계 유형',
    batch_seq                   bigint       null comment '배치 순번',
    ctlg_all_cnt                int(12)      null comment '카탈로그 전체 갯수',
    ctlg_inspt_fns_cnt          int(12)      null comment '카탈로그 검수완료 갯수',
    ctlg_chg_cnt                int(12)      null comment '카탈로그 수정 갯수',
    ctlg_chg_ratio              float(12, 1) null comment '카탈로그 수정율',
    mtch_prod_all_cnt           int(12)      null comment '매칭상품 전체 갯수',
    prod_sp_cnt                 int(12)      null comment '상품 검색특가 갯수',
    prod_inspt_fns_cnt          int(12)      null comment '상품 검수완료 갯수',
    prod_sp_inspt_fns_cnt       int(12)      null comment '상품 검색특가 검수완료 갯수',
    prod_chg_all_cnt            int(12)      null comment '상품 수정 전체 갯수',
    prod_chg_lowest_price_cnt   int(12)      null comment '상품수정  최저가 갯수',
    prod_chg_sp_cnt             int(12)      null comment '상품 수정 검색특가 갯수',
    prod_chg_ratio              float(12, 1) null comment '상품 수정율',
    prod_lowest_price_chg_ratio float(12, 1) null comment '상품 최저가 수정율',
    prod_sp_chg_ratio           float(12, 1) null comment '상품 검색특가 수정율',
    cate_nm                     varchar(50)  null comment '카테고리 이름',
    lcate_cd                    int          null comment '대분류',
    lcate_nm                    varchar(50)  null comment '세분류 명',
    mcate_cd                    int          null comment '중분류',
    mcate_nm                    varchar(50)  null comment '세분류 명',
    scate_cd                    int          null comment '소분류',
    scate_nm                    varchar(50)  null comment '세분류 명',
    dcate_cd                    int          null comment '대분류',
    dcate_nm                    varchar(50)  null comment '세분류 명',
    reg_dt                      datetime     null comment '등록일시',
    reg_id                      varchar(64)  not null comment '등록자',
    cate_cd                     varchar(45)  not null,
    primary key (anlys_dt, anlys_type)
)
    comment '모니터링 통계 카테고리별';

-- wsin_dev.mtrstat_by_worker
CREATE TABLE IF NOT EXISTS wsin_dev.mtrstat_by_worker
(
    anlys_dt                   varchar(10) not null comment '통계 일시',
    user_id                    varchar(64) not null,
    batch_seq                  bigint      null comment '배치 순번',
    ctlg_inspt_fns_cnt         int(12)     null comment '카탈로그 검수완료 갯수',
    ctlg_cnt                   int(12)     null comment '카탈로그 정상',
    ctlg_chg_cnt               int(12)     null comment '카탈로그 수정 갯수',
    mtch_prod_all_cnt          int(12)     null comment '매칭상품 전체 갯수',
    prod_chg_all_cnt           int(12)     null comment '상품 수정 전체 갯수',
    prod_chg_lowest_price_cnt  int(12)     null comment '상품수정  최저가 갯수',
    prod_chg_mtch_tgt_cnt      int(12)     null comment '상품 수정 매칭후보 갯수',
    prod_chg_mtch_excld_cnt    int(12)     null comment '상품 수정 매칭제외 갯수',
    prod_chg_ctlg_remtch_cnt   int(12)     null comment '상품 수정 카탈로그 재매칭 갯수',
    prod_chg_buycdt_remtch_cnt int(12)     null comment '상품 수정 다른구성 재매칭 갯수',
    reg_dt                     datetime    null comment '등록일시',
    reg_id                     varchar(64) not null comment '등록자',
    user_nm                    varchar(50) null comment '사용자 이름',
    emp_id                     varchar(15) null comment '사번',
    dept_id                    int(12)     null comment '부서 ID',
    ldept_id                   int(12)     null comment '대부서 ID',
    ldept_nm                   varchar(32) null comment '대부서 명',
    mdept_id                   int(12)     null comment '중부서 ID',
    mdept_nm                   varchar(32) null comment '중부서 명',
    sdept_id                   int(12)     null comment '소부서 ID',
    sdept_nm                   varchar(32) null comment '소부서 명',
    primary key (anlys_dt, user_id)
)
    comment '모니터링 통계 담당자별';

create table if not exists dept_req_prod
(
    wmp_prod_seq bigint      not null comment '위메프 상품 순번'
        primary key,
    req_dt       datetime    null comment '요청 일시',
    reg_id       varchar(64) not null comment '등록자'
)
    comment '상시특가상품';

create table if not exists dept_req_prod_anlys
(
    anlys_dt                varchar(10) not null comment '통계 일시'
        primary key,
    batch_seq               bigint      null comment '배치 순번',
    tdy_req_prod_cnt        int(12)     null comment '당일 요청 상품 갯수',
    mtched_prod_cnt         int(12)     null comment '매칭완료 상품 갯수',
    tdy_mtched_prod_cnt     int(12)     null comment '당일 매칭완료 상품 갯수',
    tdy_mtch_excld_prod_cnt int(12)     null comment '당일 매칭제외 상품 갯수',
    tdy_mtch_tgt_prod_cnt   int(12)     null comment '당일 매칭후보 상품 갯수',
    reg_dt                  datetime    null comment '등록 일시',
    reg_id                  varchar(64) null comment '등록 ID'
)
    comment '상시특가상품 통계';

create table if not exists wmp_excld_prod
(
    wmp_prod_seq bigint                             not null comment '배송2.0 상품 순번'
        primary key,
    excld_rsn    varchar(255)                       null comment '제외 이유',
    reg_id       varchar(64)                        null comment '등록자 ID',
    reg_dt       datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id       varchar(64)                        null comment '수정자 ID',
    chg_dt       datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment 'WMP 매칭제외 상품';

create table if not exists vai_products
(
    version           tinyint                    default 0                 not null comment '버전',
    seq               bigint(32) unsigned comment 'seq',
    wmp_yn            tinyint                    default 0                 null comment '위메프상품여부, 0:UNKNOWN, 1:위메프, 2:원더쇼핑',
    prod_id           varchar(32) collate latin1_bin                       not null comment '상품번호',
    partner_id        varchar(32)                                          null comment '파트너ID',
    change_time       datetime                   default CURRENT_TIMESTAMP not null comment '상품정보 수정일시',
    id_trailer        char(2) collate latin1_bin default '00'              null comment '상품번호 postfix',
    title             varchar(255)                                         null comment '상품명',
    img_url           varchar(255)                                         null comment '상품이미지',
    price             int                        default 0                 null comment '상품가격',
    link_type         tinyint                                              null comment '상품종류 0: WMP_LIVE_DEAL, 1: WMP_PROD, 2:WMP_DEAL, 3: WSP_PROD',
    lcate_cd          int                        default 0                 null comment '대카테고리코드',
    mcate_cd          int                        default 0                 null comment '중카테고리코드',
    scate_cd          int                        default 0                 null comment '소카테고리코드',
    dcate_cd          int                        default 0                 null comment '세카테고리코드',
    lcate_nm          varchar(20)                                          null comment '대카테고리명',
    mcate_nm          varchar(20)                                          null comment '중카테고리명',
    scate_nm          varchar(20)                                          null comment '소카테고리명',
    dcate_nm          varchar(20)                                          null comment '세카테고리명',
    status            tinyint                    default 0                 null comment '처리상태: 0:NONE, 1:COLLECT, 2:GROUPED',
    container         varchar(20) collate latin1_bin                       null comment '그룹의 컨테이너명',
    group_key         varchar(32) collate latin1_bin                       null comment '그룹키',
    dist              float                      default 0                 not null comment '그룹-상품간 이미지 거리 (0~1)',
    is_rep            tinyint                    default 0                 null comment '트리레벨:  0:상품, 1:GROUP_KEY, 2:PARENT_KEY',
    token_title       varchar(200)                                         null comment '추출된 상품명 토큰',
    token_model_no    varchar(200)                                         null comment '추출된 모델명 토큰',
    token_weight      varchar(50)                                          null comment '추출된 중량 토큰',
    token_type        tinyint unsigned           default 0                 null comment '토큰유형',
    extra_data        varchar(50)                                          null comment '기타정보',
    match_case        smallint                   default 0                 null comment '처리된 그룹핑 조건 (0~127)',
    score             float                      default 0                 null comment '총점 (0~400)',
    score_dist        float                      default 0                 null comment '이미지 유사도 점수 (0~1.0)',
    score_title       float                      default 0                 null comment '상품명 유사도 점수 (0~1.0)',
    score_model_no    float                      default 0                 null comment '모델명 유사도 점수 (0~1.0)',
    score_weight      float                      default 0                 null comment '중량 유사도 점수 (0~1.0)',
    brand_no          bigint(10) unsigned        default 0                 null comment '브랜드번호',
    deriv_brand_no_yn tinyint                    default 0                 null comment '상품명으로부터 브랜드번호 추출 여부, 0:brand_no, 1:상품명',
    primary key (version, prod_id)
)
    comment '상품-그룹 매핑 추천정보';

create index idx_00
    on vai_products (status, container, seq);

create index idx_02
    on vai_products (mcate_cd, id_trailer, seq);

create index idx_04
    on vai_products (group_key, is_rep, dist);

create index idx_05
    on vai_products (lcate_cd);

create index idx_06
    on vai_products (scate_cd, group_key, dcate_cd);

create index idx_seq
    on vai_products (seq);

alter table vai_products
    modify seq bigint(32) unsigned auto_increment comment 'seq';

create table if not exists wsin_recomm_groups_prod_cnt
(
    seq               bigint(32) unsigned comment 'seq',
    version           tinyint  default 0                 not null comment '버전',
    group_key         varchar(32) collate latin1_bin     not null comment 'GROUP_KEY (레벨1)',
    parent_key        varchar(32) collate latin1_bin     not null comment 'PARENT_KEY (레벨2: GROUP_KEY의 상위키)',
    product_count     int      default 0                 null comment '하위상품개수 (parent_key일때:모든하위상품수, group_key일때:하위상품수)',
    sub_product_count int      default 0                 null comment '다른 하위 그룹의 상품개수(parent_key일때: 하위그룹의 상품수, group_key일때:0)',
    chg_dt            datetime default CURRENT_TIMESTAMP not null comment '변경시각',
    primary key (group_key, version)
)
    comment '추천 클러스터링 상품 카운트';

create index IX_wsin_recomm_groups_prod_cnt
    on wsin_recomm_groups_prod_cnt (seq);

create index IX_wsin_recomm_groups_prod_cnt_1
    on wsin_recomm_groups_prod_cnt (parent_key, group_key, product_count);

alter table wsin_recomm_groups_prod_cnt
    modify seq bigint(32) unsigned auto_increment comment 'seq';

-- ---------------------------------------------------------------------------------------------------------------------
-- N사 역EP 연동
-- ---------------------------------------------------------------------------------------------------------------------
create table if not exists rep_naver_ctlg
(
    naver_ctlg_seq bigint not null comment '네이버 카탈로그 순번'
        primary key,
    naver_ctlg_nm varchar(250) null comment '네이버 카탈로그 명',
    naver_cate_cd int(12) null comment '네이버 카테고리 코드',
    ppr_yn char not null comment '인기 여부',
    lowest_price int not null comment '최저가',
    lowest_price_device varchar(10) not null comment '최저가 디바이스',
    buycdt_use_yn char not null comment '다른구성 사용 여부',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '네이버 카탈로그';

create index IX_rep_naver_ctlg_catecd
    on wsin_dev.rep_naver_ctlg (naver_cate_cd);

create table if not exists rep_naver_wsin_cate_map
(
    naver_cate_cd  int(12)                            not null comment '네이버 카테고리 코드'
        primary key,
    naver_lcate_nm varchar(50)                        null comment '네이버 대분류 명',
    naver_mcate_nm varchar(50)                        null comment '네이버 중분류 명',
    naver_scate_nm varchar(50)                        null comment '네이버 소분류 명',
    naver_dcate_nm varchar(50)                        null comment '네이버 세분류 명',
    dcate_cd       int(12)                            not null comment '세분류 코드',
    reg_id         varchar(64)                        null comment '등록 ID',
    reg_dt         datetime default CURRENT_TIMESTAMP not null comment '등록일시'
)
    comment '네이버 위메프 카테고리 맵핑';

create index IX_rep_naver_wsin_cate_map_dcatecd
    on rep_naver_wsin_cate_map (dcate_cd);

create table if not exists rep_naver_wsin_ctlg_map
(
    ctlg_seq       bigint not null comment '카탈로그 순번'
        primary key,
    naver_ctlg_seq bigint not null comment '네이버 카탈로그 순번'
)
    comment '미매칭 카탈로그 네이버 WSIN 매핑';

create index IX_rep_naver_wsin_ctlg_map_naver_wsin
    on rep_naver_wsin_ctlg_map (ctlg_seq, naver_ctlg_seq);

create table if not exists rep_recomm_excld_prod
(
    wmp_prod_seq bigint                             not null comment '위메프 상품 순번'
        primary key,
    reg_id       varchar(64)                        null comment '등록 ID',
    reg_dt       datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id       varchar(64)                        null comment '수정 ID',
    chg_dt       datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '역EP 추천제외 상품';

create table if not exists rep_unmap_ctlg
(
    naver_ctlg_seq bigint            not null comment '네이버 카탈로그 순번'
        primary key,
    ctlg_prod_cnt  int(12) default 0 null comment '카탈로그 상품 갯수'
)
    comment '미매칭 상품 카탈로그';

create table if not exists rep_unmap_prod
(
    wmp_prod_seq    bigint                             not null comment '위메프 상품 순번'
        primary key,
    naver_ctlg_seq  bigint                             not null comment '네이버 카탈로그 순번',
    naver_buycdt_nm varchar(70)                        not null comment '네이버 다른구성 명',
    reg_dt          datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_dt          datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '역EP 미매칭 상품';

create index IX_rep_unmap_prod
    on rep_unmap_prod (naver_ctlg_seq);

create index IX_rep_unmap_prod_chg_dt
    on rep_unmap_prod (chg_dt);

-- ---------------------------------------------------------------------------------------------------------------------
-- WSIN CMS 역EP 쇼핑몰 매핑 관리
-- ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE rep_mall (
    mall_std_id bigint(20)          not null    auto_increment comment '몰 표준 ID(WMP)',
    mall_nm varchar(50)             not null    comment '쇼핑몰명',
    rep_mall_id varchar(70)         null        comment '몰ID(역EP)',
    mall_type varchar(30)           null        comment '몰 유형(brand, solution, shopping, smart, normal, etc)',
    svc_show char(1) default 'Y'    not null    comment '서비스 노출(Y/N)',
    mall_url_pc varchar(200)        null        comment '쇼핑몰 URL(PC)',
    mall_url_mobile varchar(200)    null        comment '쇼핑몰 URL(모바일웹)',
    mall_img varchar(100)           null        comment '몰 로고 이미지',
    memo varchar(300)               null        comment '메모',
    landing_rule1 varchar(500)      null        comment '랜딩 규칙1',
    landing_rule2 varchar(500)      null        comment '랜딩 규칙2',
    landing_rule3 varchar(500)      null        comment '랜딩 규칙3',
    prod_rule varchar(30)           null        comment '상품 규칙',
    ws_map_yn char(1) default 'N'   not null    comment 'EP(제휴쇼핑몰) 연동(Y/N)',
    com_reg_num varchar(20)         null        comment '사업자등록번호',
    ceo_nm varchar(20)              null        comment '대표자',
    help_call varchar(20)           null        comment '고객문의 연락처',
    ceo_email varchar(100)          null        comment '대표 이메일',
    comp_addr varchar(200)          null        comment '사업장 주소',
    net_sell_num varchar(20)         null        comment '통신판매업신고번호',
    reg_id varchar(64)              not null    comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id varchar(64)              not null    comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시',
    PRIMARY KEY (mall_std_id)
) ENGINE = MyISAM
    comment 'WSIN CMS 역EP 쇼핑몰 매핑관리 매핑 테이블';

ALTER TABLE rep_mall AUTO_INCREMENT=10000001;

CREATE TABLE rep_mall_sub (
    ws_pipe_id varchar(32)          not null    comment 'EP pipe id',
    ws_mall_nm varchar(100)         not null    comment 'EP 몰 명',
    ws_mall_id integer              not null    comment 'EP 몰 ID',
    svc_status varchar(30)          null        comment '서비스 상태(service_hold, service_in, service_stop, service_out_waiting, service_out_complete)',
    billing_type varchar(10)         null        comment '과금 타입(cps, cpc, free)',
    prod_type varchar(10)            null        comment '상품 타입(normal, hot_deal)',
    mall_std_id bigint(20)          not null    comment '몰 표준 ID(WMP)',
    reg_id varchar(64)              not null    comment '등록자 ID',
    reg_dt datetime default CURRENT_TIMESTAMP not null comment '등록일시',
    chg_id varchar(64)              not null    comment '수정자 ID',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시',
    PRIMARY KEY (mall_std_id, ws_pipe_id)
) ENGINE = MyISAM
    comment 'WSIN CMS 역EP 쇼핑몰 매핑관리 매핑된 EP 몰 정보';

-- ---------------------------------------------------------------------------------------------------------------------
-- 속성값-카탈수 배치
-- ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE `batch_attr_elmt` (
                                   `attr_elmt_seq` bigint(20) NOT NULL COMMENT '속성값 순번',
                                   `attr_seq` bigint(20) NOT NULL COMMENT '속성 순번',
                                   `ctlg_count` int(11) DEFAULT '0' COMMENT '카탈로그 수',
                                   PRIMARY KEY (`attr_elmt_seq`)
) COMMENT='속성값-카탈수 배치 테이블';

-- ---------------------------------------------------------------------------------------------------------------------
-- 노출 제외 속성 키워드
-- ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE `attr_kwrd_excld` (
                                   `kwrd_seq` bigint(20) NOT NULL COMMENT '키워드 순번',
                                   `cate_cd` int(11) NOT NULL COMMENT '카테고리 코드',
                                   `cate_depth` int(1) DEFAULT NULL COMMENT '카테고리 계층',
                                   `reg_id` varchar(64) NOT NULL COMMENT '등록자',
                                   `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                                   `chg_id` varchar(64) DEFAULT NULL COMMENT '수정자',
                                   `chg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                                   PRIMARY KEY (`kwrd_seq`,`cate_cd`)
) COMMENT='노출 제외 속성 키워드';

-- ---------------------------------------------------------------------------------------------------------------------
-- 키워드 속성
-- ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE `attr_kwrd` (
                             `kwrd_seq` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '키워드 순번',
                             `kwrd_nm` varchar(500) DEFAULT NULL COMMENT '키워드 명',
                             `cate_cd` int(11) DEFAULT NULL COMMENT '카테고리 코드',
                             `cate_depth` int(1) DEFAULT NULL COMMENT '카테고리 계층',
                             `reg_id` varchar(64) NOT NULL COMMENT '등록자',
                             `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                             `chg_id` varchar(64) DEFAULT NULL COMMENT '수정자',
                             `chg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                             `brand_dsp_yn` char(1) DEFAULT NULL COMMENT '제조사/브랜드 노출 여부\n전체, Y, N',
                             `maker_brand_slt` char(1) DEFAULT 'B' COMMENT '제조사/브랜드선택 - 브랜드 ''B'' / 제조사 ''M'' _ default ''B''',
                             `attr_map_type` varchar(10) NOT NULL DEFAULT 'CATEGORY',
                             PRIMARY KEY (`kwrd_seq`),
                             UNIQUE KEY `kwrd_nm` (`kwrd_nm`),
                             KEY `idx_cate_cd` (`cate_cd`),
                             KEY `attr_map_type_idx` (`attr_map_type`)
) COMMENT='속성 키워드';

-- ---------------------------------------------------------------------------------------------------------------------
-- 키워드 속성값 매핑
-- ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE `attr_elmt_kwrd_map` (
                                      `attr_elmt_kwrd_map_seq` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `attr_kwrd_map_seq` bigint(20) NOT NULL,
                                      `attr_elmt_seq` bigint(20) NOT NULL,
                                      `filter_dsp_yn` char(1) NOT NULL DEFAULT 'Y' COMMENT '속성 키워드 속성값 매핑 테이블',
                                      `attr_elmt_dsp_odr` smallint(6) DEFAULT NULL,
                                      `reg_id` varchar(64) DEFAULT NULL COMMENT '등록자 ID',
                                      `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                                      `chg_id` varchar(64) DEFAULT NULL COMMENT '수정자 ID',
                                      `chg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                                      PRIMARY KEY (`attr_elmt_kwrd_map_seq`)
) comment '키워드 속성값 매핑 테이블';

create index attr_kwrd_map_seq_idx on attr_elmt_kwrd_map (attr_kwrd_map_seq);
create index attr_elmt_seq_idx on attr_elmt_kwrd_map (attr_elmt_seq);

-- ---------------------------------------------------------------------------------------------------------------------
-- 키워드 속성 매핑
-- ---------------------------------------------------------------------------------------------------------------------
CREATE TABLE `attr_kwrd_map` (
                                 `attr_kwrd_map_seq` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `kwrd_seq` bigint(20) NOT NULL,
                                 `attr_seq` bigint(20) NOT NULL,
                                 `filter_dsp_yn` char(1) NOT NULL DEFAULT 'Y',
                                 `attr_dsp_odr` smallint(6) DEFAULT NULL,
                                 `reg_id` varchar(64) DEFAULT NULL,
                                 `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '키워드 속성 매핑 테이블',
                                 `chg_id` varchar(64) DEFAULT NULL,
                                 `chg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`attr_kwrd_map_seq`)
) comment '키워드 속성 매핑 테이블';

create index kwrd_seq_idx on attr_kwrd_map (kwrd_seq);
create index attr_seq_idx on attr_kwrd_map (attr_seq);


CREATE TABLE wsin_ctlg_buycdt_set_prod_mapping (
   prod_no            bigint(20) UNSIGNED NOT NULL COMMENT '상품 no',
   ctlg_seq           bigint(20)          NOT NULL COMMENT '카탈로그 seq',
   buycdt_set_seq     bigint(20)          NOT NULL COMMENT '구매조건셋 seq',
   use_yn             char(1)             NOT NULL,
   chg_dt             datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
   exposure_unit_type char(6)                      DEFAULT NULL
       COMMENT '상품 노출 유형, BUYSET : 구매조건, CATALG : 카탈로그, PRODCT : 상품',
   PRIMARY KEY (prod_no),
   KEY `IX_wsin_ctlg_buycdt_set_prod_mapping_1` (ctlg_seq),
   KEY `idx_chg_dt` (chg_dt)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='WSIN 카테고리, 구매조건셋, 상품 매핑 테이블';

CREATE TABLE ctlg_attr_map_type (
     ctlg_attr_map_type_seq bigint(20) NOT NULL AUTO_INCREMENT,
     ctlg_seq bigint(20) NOT NULL COMMENT '카탈로그 ID',
     attr_seq bigint(20) NOT NULL COMMENT '속성 ID',
     attr_map_type varchar(45) NOT NULL DEFAULT 'M' COMMENT '속성 매핑 유형',
     reg_id varchar(64) DEFAULT NULL COMMENT '등록자 ID',
     reg_dt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
     chg_id varchar(64) DEFAULT NULL COMMENT '수정자 ID',
     chg_dt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
     PRIMARY KEY (ctlg_attr_map_type_seq),
     UNIQUE KEY ctlg_attr_unq (ctlg_seq,attr_seq),
     KEY ctlg_idx (ctlg_seq)
)COMMENT='카탈로그 속성 매핑 타입 테이블';

create table if not exists user_category_move (
    user_cate_seq bigint(20) NOT NULL AUTO_INCREMENT COMMENT '유저카테고리순번 ID',
    user_id varchar(64) NOT NULL COMMENT '유저 ID',
    dcate_cd int(11) NOT NULL COMMENT '세분류 코드',
    reg_dt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
    PRIMARY KEY (user_cate_seq),
    UNIQUE KEY user_cate_move_uindex (user_id,dcate_cd),
    KEY user_cate_idx (user_id)
    ) COMMENT='유저 카테고리 이동 테이블';

CREATE TABLE `dcate_cate_map` (
    dcate_cate_map_seq bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
    dcate_cd bigint(20) NOT NULL COMMENT 'dcate_cd ID',
    cate_cd bigint(20) NOT NULL COMMENT '매핑 카테고리 ID',
    PRIMARY KEY (`dcate_cate_map_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
