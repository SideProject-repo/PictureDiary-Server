select 1 ;
create schema if not exists wsin_wmp_deal_dev;
use wsin_wmp_deal_dev;

-- wsin_wmp_deal_dev.prod_sale
create table if not exists wsin_wmp_deal_dev.prod_sale
(
    prod_no bigint unsigned not null comment '상품번호'
        primary key,
    sale_period char default '' not null comment '판매기간 기준 - 상시판매 / 기간설정 (Always / Period)',
    sale_start_dt datetime null comment '판매기간시작',
    sale_end_dt datetime null comment '판매기간종료',
    origin_price int(11) unsigned null comment '정상가격',
    sale_price int(11) unsigned null comment '판매가격',
    tax_yn char null comment '부가세 여부(Y:과세,N:비과세)',
    affi_purchase_limit_yn char default 'N' not null comment '제휴진입구매제한여부 (Y:제한설정, N:제한없음)',
    basket_limit_yn char null comment '장바구니 담기 제한(Y:제한설정, N:제한없음)',
    purchase_min_count smallint unsigned default 1 not null comment '구매제한 - 최소구매수량',
    purchase_limit_yn char null comment '1인당 구매제한 사용여부 - 미사용:Y / 사용:N',
    purchase_limit_duration char null comment '구매제한 타입 - 1회 : O / 기간제한 : P',
    purchase_limit_day smallint unsigned null comment '구매제한 일자 - ?일',
    purchase_limit_count smallint unsigned null comment '구매제한 개수 - ?개',
    ref_price_type varchar(5) null comment '기준가격 근거정보 \n- 온라인/마트/백화점 등등',
    ref_price_proof_type char null comment '기준가격 근거정보 구분 (URL : U, 미등록사유 : R)',
    ref_price_proof_info varchar(200) null comment '기준가격 근거정보 - URL / 미등록사유',
    ref_price_img_nm varchar(45) null comment '이미지명',
    ref_price_img varchar(200) null comment '이미지',
    ref_price_date date null comment '기준가격 일자',
    commission_type varchar(2) null comment '수수료 부과 기준 : 정률 Fixed Rate / 정액 Fixed Amount',
    commission_rate decimal(5,2) null comment '수수료율(%)',
    commission_price int unsigned null comment '수수료금액',
    reg_id varchar(20) not null comment '등록자',
    reg_dt datetime not null comment '등록일시',
    chg_id varchar(20) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '상품판매정보' charset=utf8;

ALTER TABLE prod_sale ADD INDEX (chg_dt);
ALTER TABLE prod_sale ADD INDEX (reg_dt);
ALTER TABLE prod_sale ADD INDEX (sale_end_dt);
ALTER TABLE prod_sale ADD INDEX (sale_start_dt, sale_end_dt);

-- wsin_wmp_deal_dev.prod
create table if not exists wsin_wmp_deal_dev.prod
(
    prod_no bigint unsigned auto_increment comment '상품번호'
        primary key,
    prod_nm varchar(100) not null comment '상품명',
    mall_type char(3) default 'NOR' not null comment '판매유형 (NOR:일반-기본값, HDN:히든프라이스, BIZ:비즈몰) - 공통코드 추가',
    prod_type char default 'B' not null comment '상품유형 (B:반품(리세일), N:새상품, O:위메프 사외창고, R:리퍼, U:중고)',
    prod_status char default '' not null comment '상품상태(T: 임시저장, ,B:진행대기, A:판매중, S:판매중지, E:수정대기)',
    sale_status char default '' not null comment '판매상태(A:판매중,S:품절)',
    dcate_cd int unsigned not null comment '세카테고리',
    partner_id varchar(20) default '' not null comment '파트너ID',
    trans_type char default '' not null comment '거래유형 (C : 위탁 , D : 직매입)',
    sale_status_dt datetime null comment '판매중지처리일시',
    adult_limit_yn char default 'N' null comment '19금 제한여부 (Y:제한사용,N:제한미사용)',
    local_liquor_yn char default 'N' null comment '전통주 여부 (Y:전통주, N:전통주 아님)',
    disp_yn char null comment '노출정보',
    detail_type varchar(5) default '' not null comment '상세설명타입 (IMG:이미지,HTML:html)',
    brand_no bigint(10) unsigned null comment '브랜드번호',
    maker_no bigint(10) unsigned null comment '제조사번호',
    opt_sel_use_yn char default '' null comment '선택옵션사용여부 (사용:Y,미사용:N)',
    opt_txt_use_yn char default '' null comment '텍스트옵션사용여부 (사용:Y,미사용:N)',
    reg_id varchar(20) not null comment '등록자',
    reg_dt datetime not null comment '등록일시',
    chg_id varchar(20) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '상품 기본정보' charset=utf8;

ALTER TABLE prod ADD INDEX (dcate_cd);
ALTER TABLE prod ADD INDEX (reg_id);
ALTER TABLE prod ADD INDEX (chg_dt);
ALTER TABLE prod ADD INDEX (partner_id);
ALTER TABLE prod ADD INDEX (maker_no);
ALTER TABLE prod ADD INDEX (brand_no);
ALTER TABLE prod ADD INDEX (dcate_cd, prod_status, sale_status, reg_dt);
ALTER TABLE prod ADD INDEX (dcate_cd, prod_status, sale_status, partner_id, reg_dt);
ALTER TABLE prod ADD INDEX (reg_dt);

-- wsin_wmp_deal_dev.category_divide
create table if not exists wsin_wmp_deal_dev.category_divide
(
    dcate_cd int(11) unsigned auto_increment comment '세분류(400000)'
        primary key,
    lcate_cd int(11) unsigned not null comment '대분류',
    mcate_cd int(11) unsigned not null comment '중분류',
    scate_cd int(11) unsigned not null comment '소분류',
    dcate_nm varchar(50) not null comment '세분류 명',
    priority smallint(6) unsigned not null comment '우선순위',
    disp_yn char not null comment '노출여부(전체노출:Y/어드민만 노출:A/비노출:N)',
    use_yn char not null comment '사용여부(사용:Y/미사용:N)',
    reg_id varchar(10) not null comment '등록자',
    reg_dt datetime not null comment '등록일시',
    chg_id varchar(10) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '세카테고리' charset=utf8;

ALTER TABLE category_divide ADD INDEX (scate_cd);
ALTER TABLE category_divide ADD INDEX (mcate_cd);
ALTER TABLE category_divide ADD INDEX (use_yn, disp_yn);
ALTER TABLE category_divide ADD INDEX (chg_dt);
ALTER TABLE category_divide ADD INDEX (reg_dt);

-- wsin_wmp_deal_dev.category_group
create table if not exists wsin_wmp_deal_dev.category_group
(
    gcate_cd int(11) unsigned auto_increment
        primary key,
    gcate_nm varchar(10) not null comment '대대분류명',
    use_yn char not null comment '사용여부',
    best_new_disp_yn char not null comment '베스트/신규상품 노출 여부',
    mobile_icon_use_yn char not null comment '모바일아이콘 사용여부',
    priority tinyint(3) default 99 not null comment '우선순위',
    reg_id varchar(10) not null comment '수정자',
    reg_dt datetime not null comment '수정일',
    chg_id varchar(10) not null comment '등록자',
    chg_dt datetime not null comment '등록일'
)
    comment '대대분류' charset=utf8;

-- wsin_wmp_deal_dev.category_large
create table if not exists wsin_wmp_deal_dev.category_large
(
    lcate_cd int(11) unsigned auto_increment comment '대분류(100000)'
        primary key,
    lcate_nm varchar(50) not null comment '대분류 명',
    priority smallint(6) unsigned not null comment '우선순위',
    disp_yn char not null comment '노출여부(전체노출:Y/어드민만 노출:A/비노출:N)',
    use_yn char not null comment '사용여부(사용:Y/미사용:N)',
    reg_id varchar(10) not null comment '등록자',
    reg_dt datetime not null comment '등록일시',
    chg_id varchar(10) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '대카테고리' charset=utf8;

ALTER TABLE category_large ADD INDEX (use_yn, disp_yn);
ALTER TABLE category_large ADD INDEX (chg_dt);
ALTER TABLE category_large ADD INDEX (reg_dt);

-- wsin_wmp_deal_dev.category_middle
create table if not exists wsin_wmp_deal_dev.category_middle
(
    mcate_cd int(11) unsigned auto_increment comment '중분류(200000)'
        primary key,
    lcate_cd int(11) unsigned not null comment '대분류',
    mcate_nm varchar(50) not null comment '중분류 명',
    priority smallint(6) unsigned not null comment '우선순위',
    disp_yn char not null comment '노출여부(전체노출:Y/어드민만 노출:A/비노출:N)',
    use_yn char not null comment '사용여부(사용:Y/미사용:N)',
    reg_id varchar(10) not null comment '등록자',
    reg_dt datetime null comment '등록일시',
    chg_id varchar(10) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '중카테고리' charset=utf8;

ALTER TABLE category_middle ADD INDEX (lcate_cd);
ALTER TABLE category_middle ADD INDEX (use_yn, disp_yn);
ALTER TABLE category_middle ADD INDEX (chg_dt);
ALTER TABLE category_middle ADD INDEX (reg_dt);

-- wsin_wmp_deal_dev.category_small
create table if not exists wsin_wmp_deal_dev.category_small
(
    scate_cd int(11) unsigned auto_increment comment '소분류(300000)'
        primary key,
    lcate_cd int(11) unsigned not null comment '대분류',
    mcate_cd int(11) unsigned not null comment '중분류',
    scate_nm varchar(50) not null comment '소분류 명',
    priority smallint(6) unsigned not null comment '우선순위',
    disp_yn char not null comment '노출여부(전체노출:Y/어드민만 노출:A/비노출:N)',
    use_yn char not null comment '사용여부(사용:Y/미사용:N)',
    reg_id varchar(10) not null comment '등록자',
    reg_dt datetime not null comment '등록일시',
    chg_id varchar(10) null comment '수정자',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '소카테고리' charset=utf8;

ALTER TABLE category_small ADD INDEX (mcate_cd);
ALTER TABLE category_small ADD INDEX (use_yn, disp_yn);
ALTER TABLE category_small ADD INDEX (chg_dt);
ALTER TABLE category_small ADD INDEX (reg_dt);

-- wsin_wmp_deal_dev.partner_seller
create table if not exists  wsin_wmp_deal_dev.partner_seller
(
    partner_id varchar(20) not null comment '파트너_아이디'
        primary key,
    seller_type varchar(8) default 'NORMAL' not null comment '회원구분 (일반: NORMAL, 그룹: GROUP)',
    business_nm varchar(50) not null comment '판매_업체_명',
    company_info varchar(500) null comment '회사_정보',
    homepage varchar(100) null comment '회사_홈페이지',
    sms_yn char null comment 'SMS 수신동의 여부',
    sms_yn_dt datetime null comment 'SMS수신여부수정일시',
    email_yn char null comment '이메일 수신동의 여부',
    email_yn_dt datetime null comment '이메일수신여부수정일시',
    warehouse_yn char default 'N' null comment '물류센터 위탁여부(Y:사용,N:사용안함)',
    eticket_order_yn char default 'N' null comment 'e-티켓 주문조회 여부 (Y: 사용 / N:사용안함)',
    delivery_info_yn char null comment '반품택배사 정보유무(Y:있음, N:없음)',
    pickup_proxy_yn char default 'Y' not null comment '수거대행여부(Y:대행가능,N:대행불가)',
    claim_fee_enclose_yn char default 'N' not null comment '반품배송비 동봉 여부',
    pickup_enable_yn char default 'Y' not null comment '구매자 수거송장 입력 여부',
    part_claim_enable_yn char default 'Y' not null comment '수량부분취소/반품 여부',
    cancel_pending_yn char default 'N' not null comment '취소 보류 허용 여부',
    deal_group_id int unsigned default 500000000 null comment '상품군아이디( 배송상품:200000000, 여행/숙박/레저:300000000, 공연/전시/체험:400000000, 지역할인쿠폰:100000000, 기타(예:온라인 교육):500000000 ) 배송2.0: 판매상품유형',
    theme_id int unsigned default 0 not null comment '테마 아이디 (카테고리 부분) 레거시 필드명 동일, 배송2.0: 대표 카테고리',
    md_emp_no varchar(20) null comment '상품 담당 엠디 사번',
    sales_emp_no varchar(20) null comment '영업 담당 MD 사번 ',
    discount_agree_dt datetime null comment '파트너 즉시할인 이용동의 일시',
    wmp_support_discount_yn char default 'N' not null comment '위메프 지원 할인프로그램 동의 여부(Y:동의, N:동의안함)',
    deduction_offer_yn char default 'N' not null comment '소득공제 제공여부',
    deduction_offer_no varchar(20) null comment '소득공제 제공 사업자 식별번호',
    affi_chg_dt datetime default CURRENT_TIMESTAMP null comment '제휴 수정일',
    chg_fee_agree char null comment '수수료율 변경 동의여부',
    reg_dt datetime not null comment '등록_일시',
    reg_id varchar(20) not null comment '등록자_아이디',
    chg_dt datetime default CURRENT_TIMESTAMP not null comment '수정일시',
    chg_id varchar(20) not null comment '수정자_아이디',
    platform varchar(8) default 'PC' not null comment '회원가입 플랫폼(PC/MOBILE)'
)
    comment '파트너 판매자 정보' charset=utf8;

ALTER TABLE partner_seller ADD INDEX (business_nm);
ALTER TABLE partner_seller ADD INDEX (chg_dt);
ALTER TABLE partner_seller ADD INDEX (reg_dt);

CREATE TABLE IF NOT EXISTS partner_seller_ship (
    ship_policy_no bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '배송 정책번호',
    partner_id varchar(20) NOT NULL COMMENT '업체아이디',
    default_yn char(1) NOT NULL COMMENT '기본여부(Y,N)',
    ship_policy_nm varchar(50) NOT NULL DEFAULT '' COMMENT '배송 정책명',
    ship_mng varchar(6) NOT NULL COMMENT '배송주체 (Seller:파트너,WemakPrice Logistics:물류센터, Outside Warehouse:사외창고)',
    warehouse_inside varchar(10) DEFAULT NULL COMMENT '물류 센터',
    warehouse_outside varchar(10) DEFAULT NULL COMMENT '사외 창고',
    ship_method varchar(10) DEFAULT NULL COMMENT '배송방법 ( KP : 택배배송 / KD : 직접배송 / OA : 해외구매대행 )',
    release_day smallint(6) DEFAULT NULL COMMENT '출고 기한(-1:미정)',
    release_time smallint(6) unsigned DEFAULT NULL COMMENT '출고기한 1일 인 경우 출고시간(1~24)',
    holiday_except_yn char(1) DEFAULT NULL COMMENT '휴일제외여부',
    bundle_kind varchar(45) DEFAULT NULL COMMENT '배송유형(상품별/묶음배송)',
    ship_type varchar(10) DEFAULT NULL COMMENT '배송비 유형 ( FREE : 무료 / FIXED : 유료 / COND : 조건부 무료 )',
    ship_fee mediumint(8) unsigned DEFAULT NULL COMMENT '배송비',
    ship_fee_disp_yn char(1) NOT NULL DEFAULT 'Y' COMMENT '배송비 노출여부 (Y:노출,N:비노출)',
    free_condition mediumint(8) unsigned DEFAULT NULL COMMENT '무료조건비',
    diff_yn char(1) NOT NULL COMMENT '차등설정',
    diff_count smallint(6) unsigned DEFAULT NULL COMMENT '차등개수',
    prepayment_yn char(1) NOT NULL COMMENT '선결제여부',
    claim_ship_fee mediumint(8) unsigned NOT NULL COMMENT '반품/교환 배송비',
    ship_area varchar(20) DEFAULT 'WHOLE' COMMENT '배송가능지역',
    jeju_ship_fee mediumint(8) unsigned DEFAULT '0' COMMENT '제주 배송비',
    island_mountain_ship_fee mediumint(8) unsigned DEFAULT '0' COMMENT '도서 산간 배송비',
    release_zipcode varchar(7) NOT NULL COMMENT '출고지 우편번호',
    release_addr_1 varchar(255) NOT NULL COMMENT '출고지 주소1(우편번호제공)',
    release_addr_2 varchar(255) NOT NULL COMMENT '출고지 주소2(직접입력)',
    release_road_addr_1 varchar(255) DEFAULT NULL COMMENT '출고지 주소 도로명1(우편번호제공)',
    release_road_addr_2 varchar(255) DEFAULT NULL COMMENT '출고지 주소 도로명2(직접입력)',
    return_zipcode varchar(7) NOT NULL COMMENT '회수지_우편번호',
    return_addr_1 varchar(255) NOT NULL COMMENT '회수지 주소1(우편번호제공)',
    return_addr_2 varchar(255) NOT NULL COMMENT '회수지 주소2(직접입력)',
    return_road_addr_1 varchar(255) DEFAULT NULL COMMENT '회수지 주소 도로명1(우편번호제공)',
    return_road_addr_2 varchar(255) DEFAULT NULL COMMENT '회수지 주소 도로명2(직접입력)',
    use_yn char(1) NOT NULL COMMENT '사용여부',
    safety_no_disp_yn char(1) NOT NULL DEFAULT 'Y' COMMENT '안심번호 서비스 사용 노출 여부 ',
    reg_dt datetime NOT NULL COMMENT '등록일시',
    reg_id varchar(20) NOT NULL COMMENT '등록자',
    chg_dt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    chg_id varchar(20) DEFAULT NULL COMMENT '수정자',
    PRIMARY KEY (ship_policy_no),
    KEY idx_partner_use (partner_id,use_yn),
    KEY ix_n_partner_seller_ship_reg_dt (reg_dt),
    KEY ix_n_partner_seller_ship_chg_dt (chg_dt),
    KEY idx_partner_default (partner_id,default_yn)
) COMMENT '배송정책' CHARSET=utf8 ;


create table if not exists wsin_wmp_deal_dev.brand
(
    brand_no     bigint(10) unsigned auto_increment comment '브랜드번호'
        primary key,
    brand_nm     varchar(30)               not null comment '브랜드명',
    brand_nm_eng varchar(30)               null comment '브랜드명(영문)',
    maker_no     bigint unsigned           null comment '제조사번호',
    img_url      varchar(200)              null comment '이미지경로',
    img_width    mediumint unsigned        null comment '이미지가로',
    img_height   mediumint unsigned        null comment '이미지 세로',
    brand_desc   varchar(300)              null comment '브랜드설명',
    use_yn       char        default 'Y'   not null comment '사용여부',
    prod_disp_yn char        default 'N'   not null comment '상품명 노출여부',
    disp_nm      varchar(3)  default 'KOR' not null comment '전시명',
    ep_use_yn    char        default 'Y'   not null comment 'EP정보 사용여부',
    reg_id       varchar(20)               not null comment '등록자',
    reg_dt       datetime                  not null comment '등록일시',
    chg_id       varchar(20) default ''    not null comment '수정자',
    chg_dt       datetime                  not null comment '수정일시',
    site_url     varchar(200)              null comment '사이트URL'
)
    comment '브랜드' charset = utf8;

create index idx_brand_a1
    on wsin_wmp_deal_dev.brand (brand_nm);

create index idx_brand_a2
    on wsin_wmp_deal_dev.brand (brand_nm_eng);

create table if not exists wsin_wmp_deal_dev.maker
(
    maker_no     bigint(10) unsigned auto_increment comment '제조사번호'
        primary key,
    maker_nm     varchar(30)                        not null comment '제조사명',
    maker_nm_eng varchar(30)                        null comment '제조사명(영문)',
    site_url     varchar(200)                       null comment '사이트URL',
    use_yn       char     default 'Y'               not null comment '사용여부',
    reg_id       varchar(20)                        not null comment '등록자',
    reg_dt       datetime                           not null comment '등록일시',
    chg_id       varchar(20)                        null comment '수정자',
    chg_dt       datetime default CURRENT_TIMESTAMP not null comment '수정일시'
)
    comment '제조사' charset = utf8;

create index idx_maker_nm_maker_nm_eng
    on wsin_wmp_deal_dev.maker (maker_nm, maker_nm_eng);

CREATE TABLE IF NOT EXISTS prod_etc
(
    prod_no bigint(20) unsigned NOT NULL COMMENT '상품번호',
    parallel_import_yn char(1) NOT NULL COMMENT '병행수입여부(Y:대상,N:비대상)',
    md_emp_no varchar(10) NOT NULL COMMENT 'MD 사번',
    amd_emp_no varchar(10) DEFAULT NULL COMMENT 'AMD 사번',
    designer_emp_no varchar(10) DEFAULT NULL COMMENT '디자이너 사번',
    photographer_emp_no varchar(10) DEFAULT NULL COMMENT '포토그래퍼 사번',
    seller_prod_cd varchar(50) DEFAULT NULL COMMENT '업체상품코드',
    ep_yn char(1) NOT NULL DEFAULT 'N' COMMENT '가격비교 등록여부',
    contents_making_type char(1) DEFAULT NULL COMMENT '컨텐츠 제작 유형 (W:위메프, P:파트너)',
    disp_only_deal_yn char(1) DEFAULT 'N' COMMENT '딜에서만 노출 (Y:딜에서만 노출, N:제한없음)',
    isbn13 varchar(30) DEFAULT NULL COMMENT '13자리 isbn 코드',
    isbn10 varchar(30) DEFAULT NULL COMMENT '10자리 isbn코드',
    event_approve_yn char(1) DEFAULT NULL COMMENT '행사 판매촉진 동의 여부',
    wmp_agree_proc_yn char(1) DEFAULT 'N' COMMENT '위메프 합의 진행 여부(Y:합의진행, N:즉시반영)',
    review_disp char(1) DEFAULT NULL COMMENT '구매후기 노출제한(N:평점+후기노출, P:평점만 노출)',
    reg_dt datetime NOT NULL COMMENT '등록일시',
    chg_id varchar(20) DEFAULT NULL COMMENT '수정자',
    chg_dt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
    reg_id varchar(20) NOT NULL COMMENT '등록자',
    PRIMARY KEY (prod_no),
    KEY idx_md (md_emp_no),
    KEY idx_prod_etc_2 (isbn13),
    KEY idx_prod_etc_a3 (seller_prod_cd)
) COMMENT '상품기타정보' DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS prod_ship (
     prod_no bigint(20) unsigned NOT NULL COMMENT '상품번호',
     ship_policy_no int(10) unsigned NOT NULL COMMENT '배송비 정책',
     release_day smallint(6) DEFAULT NULL COMMENT '출고 기한(-1:미정)',
     release_time smallint(6) unsigned DEFAULT NULL COMMENT '출고기한 1일 인 경우 출고시간(1~24)',
     holiday_except_yn char(1) DEFAULT NULL COMMENT '휴일제외여부',
     claim_ship_fee mediumint(8) unsigned DEFAULT NULL COMMENT '반품/교환 배송비',
     release_zipcode varchar(7) DEFAULT NULL COMMENT '출고지 우편번호',
     release_addr_1 varchar(255) DEFAULT NULL COMMENT '출고지 주소1(우편번호제공)',
     release_addr_2 varchar(255) DEFAULT NULL COMMENT '출고지 주소2(직접입력)',
     release_road_addr_1 varchar(255) DEFAULT NULL COMMENT '출고지 주소 도로명1(우편번호제공)',
     release_road_addr_2 varchar(255) DEFAULT NULL COMMENT '출고지 주소 도로명2(직접입력)',
     return_zipcode varchar(7) DEFAULT NULL COMMENT '회수지_우편번호',
     return_addr_1 varchar(255) DEFAULT NULL COMMENT '회수지 주소1(우편번호제공)',
     return_addr_2 varchar(255) DEFAULT NULL COMMENT '회수지 주소2(직접입력)',
     return_road_addr_1 varchar(255) DEFAULT NULL COMMENT '회수지 주소 도로명1(우편번호제공)',
     return_road_addr_2 varchar(255) DEFAULT NULL COMMENT '회수지 주소 도로명2(직접입력)',
     reg_id varchar(20) NOT NULL COMMENT '등록자',
     reg_dt datetime NOT NULL COMMENT '등록일시',
     chg_id varchar(20) DEFAULT NULL COMMENT '수정자',
     chg_dt datetime DEFAULT NULL COMMENT '수정일시',
     PRIMARY KEY (prod_no),
     KEY idx_prod_ship_1 (chg_dt)
) COMMENT '상품배송정보' CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS prod_label (
    prod_no bigint(20) unsigned NOT NULL COMMENT '상품번호',
    label_no bigint(20) unsigned NOT NULL COMMENT '라벨번호',
    use_yn char(1) NOT NULL COMMENT '사용여부',
    reg_id varchar(20) NOT NULL COMMENT '등록자',
    reg_dt datetime NOT NULL COMMENT '등록일시',
    chg_id varchar(20) DEFAULT NULL COMMENT '수정자',
    chg_dt datetime DEFAULT NULL COMMENT '수정일시',
    PRIMARY KEY (prod_no,label_no),
    KEY idx_prod_use (prod_no,use_yn),
    KEY idx_msr_prod_label_a1 (chg_dt)
) COMMENT '상품 라벨' CHARSET=utf8;
