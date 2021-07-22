create table inquiry (
	inquiry_id int unsigned not null auto_increment,
	inquiry_type int unsigned not null,  # 문의 유형
    product_id int unsigned not null,  # 문의 하고자 하는 상품 아이디
    user_id int unsigned not null,  # 문의 하는 유저 아이디 (user 테이블 pk 와 와래키 관계)
    content varchar(200) not null,  # 문의 내용
    primary key (inquiry_id),
    foreign key (user_id) references user(user_id)
    on delete cascade
    on update cascade
);