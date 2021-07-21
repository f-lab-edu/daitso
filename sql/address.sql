create table address(
    address_id int unsigned not null auto_increment,
    zipcode varchar(20) not null, #우편번호
    si varchar(10) not null, #시
    gu varchar(10) not null, #구
    dong varchar(10) not null, #동
    address varchar(30) not null, #상세주소
    user_id int unsigned, # user 테이블 pk (이거 외래키로 쓸거임)
    primary key (id),
    foreign key (userId) references user(id)
    on delete cascade
    on update cascade
);

