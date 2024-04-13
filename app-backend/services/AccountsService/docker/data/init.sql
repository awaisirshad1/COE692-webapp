create database if not exists coe692data_accounts;
create user if not exists 'awaislocal'@'127.0.0.1' identified with mysql_native_password by 'awaislocal';
# create user if not exists 'awaislocal'@'%' identified with  'awaislocal';
grant all privileges on coe692data_accounts.* to 'awaislocal'@'%';
grant all privileges on coe692data_accounts.* to 'awaislocal'@'127.0.0.1';
flush privileges;
# use coe692data_accounts
# create table if not exists User
# (
#     username   varchar(16)          not null,
#     password   varchar(30)          not null,
#     first_name varchar(30)          not null,
#     last_name  varchar(30)          not null,
#     is_trainer tinyint(1) default 0 null,
#     primary key (username)
# );
USE coe692data_accounts;

-- Create the table if it does not exist
CREATE TABLE IF NOT EXISTS User
(
    username   VARCHAR(16) NOT NULL,
    password   VARCHAR(30) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    is_trainer TINYINT(1) DEFAULT 0 NULL,
    PRIMARY KEY (username)
);

insert into User(username, password, first_name, last_name, is_trainer)
values ('dummy1', 'dummypw', 'dummyfn', 'dummyln', false);
