create database if not exists coe692data_trainers;
create user if not exists 'awaislocal'@'127.0.0.1' identified with mysql_native_password by 'awaislocal';
grant all privileges on coe692data_trainers.* to 'awaislocal'@'%';
grant all privileges on coe692data_trainers.* to 'awaislocal'@'127.0.0.1';
flush privileges;
USE coe692data_trainers;

-- Create the table if it does not exist
CREATE TABLE IF NOT EXISTS trainer
(
    username   VARCHAR(16) NOT NULL,
    PRIMARY KEY (username)
);

insert into trainer(username)
values ('dummy1');

create table if not exists client_records
(
    username varchar(16) not null,
    trainer_username varchar(16) not null,
    health_goal varchar(40),
    dietary_preferences varchar(40),
    weight decimal(5,2),
    height decimal(5,2),
    age int,
    primary key (username)
);

insert into client_records  (username, trainer_username, health_goal, dietary_preferences, weight, height, age)
values ('dummy2', 'dummy1', '120lbs', 'Halal', 145.5, 183.2, 30);


