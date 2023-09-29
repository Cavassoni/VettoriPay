create table if not exists user
(
    id       binary(16)              not null
        primary key,
    cpf      varchar(14)             not null,
    email    varchar(100)            null,
    name     varchar(150)            not null,
    password varchar(100)            null,
    phone    varchar(16)             null,
    user_type enum ('LOGIST', 'USER') not null,
    constraint UK_2qv8vmk5wxu215bevli5derq
        unique (cpf),
    constraint UK_589idila9li6a4arw1t8ht1gx
        unique (phone),
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email)
);

create table if not exists wallet
(
    id      binary(16)     not null
        primary key,
    balance decimal(14, 2) not null,
    user_id binary(16)     not null,
    constraint UK_hgee4p1hiwadqinr0avxlq4eb
        unique (user_id),
    constraint FKbs4ogwiknsup4rpw8d47qw9dx
        foreign key (user_id) references user (id)
);

