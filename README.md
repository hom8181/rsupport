# 공지사항 등록, 수정, 삭제, 조회 API 입니다.

## 개발 환경
- Java 17
- Spring Boot 3.3.1
- Hibernate
- MySQL 
url : jdbc:mysql://127.0.0.1:3306/notice
username : root
password : 1234


## Database DDL
create table notice
(
    nt_idx                 int auto_increment comment '공지사항 IDX' primary key,
    title                  varchar(255) null comment '제목',
    content                varchar(255) null comment '내용',
    notice_start_date_time datetime     null comment '공지사항 시작 날짜',
    notice_end_date_time   datetime     null comment '공지사항 종료 날짜',
    hits                   int          null comment '조회수',
    author                 varchar(20)  null comment '작성자',
    registration_date_time datetime     null comment '등록 날짜',
    modified_date_time     datetime     null comment '수정 날짜'
)   comment '공지사항';

create table notice_attachment
(
    na_idx     int auto_increment comment '공지사항 파일 IDX'
        primary key,
    notice     int          null comment '공지사항 IDX',
    name       varchar(100) null comment '파일명',
    byte_array longblob     null comment 'byteArray',
    constraint notice_attachment_notice_nt_idx_fk
        foreign key (notice) references notice (nt_idx)
            on update cascade on delete cascade
);


## API 구성
- 'POST /notice'     : 공지사항 등록
- 'GET /notices'     : 공지사항 리스트 조회
- 'GET /notice/{id}' : 공지사항 상세 조회
- 'PUT /notice'      : 공지사항 수정
- 'DELETE /notice'   : 공지사항 삭제


## 테스트 영상
https://github.com/user-attachments/assets/92c423b2-5fe6-44db-a546-7bbf0cae9d6f
