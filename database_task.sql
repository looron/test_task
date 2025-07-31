
create database task_bd;
use task_bd;

SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

create table tasks 
(
idTask int primary key auto_increment,
title text  NOT NULL,
descriotion text NOT NULL,
dedline date not null,
is_complite bool default false
);