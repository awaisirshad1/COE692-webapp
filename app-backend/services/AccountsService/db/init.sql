create database if not exists coe692data_accounts;
grant all privileges on coe692data_accounts.* to 'awaislocal'@'%' identified by 'awaislocal';
grant all privileges on coe692data_accounts.* to 'awaislocal'@'localhost' IDENTIFIED by 'awaislocal';
use coe692data_accounts
create table if not exists User
(
    username   varchar(16)          not null,
    password   varchar(30)          not null,
    first_name varchar(30)          not null,
    last_name  varchar(30)          not null,
    is_trainer tinyint(1) default 0 null,
    primary key (username)
);

insert into User(username, password, first_name, last_name, is_trainer) values (dummy1, dummypw, dummyfn, dummyln, false);
