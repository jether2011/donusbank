create table tb_account (
    id bigserial not null,
    amount numeric(19, 2),
    branch int4 not null,
    number varchar(16),
    person_id int8 not null,
    primary key (id)
);

create table tb_person (
    id bigserial not null,
    name varchar(255),
    primary key (id)
);

create table tb_user (
    id bigserial not null,
    password varchar(255),
    username varchar(60),
    primary key (id)
);

create table tb_authority (
    name varchar(50) not null,
    primary key (name)
);

create table tb_user_authority (
    user_id int8 not null,
    authority_name varchar(50) not null,
    primary key (user_id, authority_name)
);

alter table tb_account add constraint UK_ohwynelw9vuwkm7mqng98mng7 unique (person_id);
alter table tb_account add constraint UK_5r88eemotwgru6k0ilqb2ledh unique (number);
alter table tb_user add constraint UK_4wv83hfajry5tdoamn8wsqa6x unique (username);
alter table tb_account add constraint FKi4ms63emb4omxhg5h4flbls8u foreign key (person_id) references tb_person;
alter table tb_user_authority add constraint FK5d633tf8gwjeyjhd3dpnb3ol foreign key (authority_name) references tb_authority;
alter table tb_user_authority add constraint FKnxttwn2pbv55si8kyik33tb3y foreign key (user_id) references tb_user;
