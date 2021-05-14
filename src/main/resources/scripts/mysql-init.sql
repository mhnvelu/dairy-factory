DROP DATABASE if exists dairyfactoryservice;
drop user if exists `dairy_factory_service`@`%`;
create database if not exists dairyfactoryservice character set utf8mb4 collate utf8mb4_unicode_ci;
create user if not exists `dairy_factory_service`@`%` IDENTIFIED with mysql_native_password by 'password';
grant select, insert, update, delete, create, drop, references, index, alter, execute, CREATE,
  create view, show view, create routine, alter routine, event, trigger on `dairyfactoryservice`.* to
  `dairy_factory_service`@`%`;
flush privileges;