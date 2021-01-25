create table tb_account (
    id bigserial not null,
    amount numeric(19, 2),
    branch int4 not null,
    number varchar(16),
    bank varchar(60) not null,
    bank_number int4 not null,
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

create table tb_person (
    id bigserial not null,
    name varchar(255),
    tax_identifier varchar(11),
    type varchar(10),
    account_id int8 not null,
    user_id int8 not null,
    primary key (id)
);

alter table tb_person add constraint UK_ohwynelw9vuwkm7mqng98mng7 unique (user_id);
alter table tb_person add constraint UK_abcdnelw1vuwkm7mqng66mng9 unique (account_id);
alter table tb_person add constraint UK_efghnelw1vuwkm7mqng22mng3 unique (tax_identifier);

alter table tb_account add constraint UK_5r88eemotwgru6k0ilqb2ledh unique (number);

alter table tb_user add constraint UK_4wv83hfajry5tdoamn8wsqa6x unique (username);

alter table tb_person add constraint FKi4ms63emb4omxhg5h4flbls8u foreign key (user_id) references tb_user;
alter table tb_person add constraint FKj5ms11emb4omxhg5h4flbls2u foreign key (account_id) references tb_account;

alter table tb_user_authority add constraint FK5d633tf8gwjeyjhd3dpnb3ol foreign key (authority_name) references tb_authority;
alter table tb_user_authority add constraint FKnxttwn2pbv55si8kyik33tb3y foreign key (user_id) references tb_user;

create index tax_identifier_idx ON tb_person using btree (tax_identifier);
create index account_number_idx ON tb_account using btree (number);