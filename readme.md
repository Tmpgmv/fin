1. Применяемые технологии
Ubuntu 24.04
PostgreSQД 17.5

2. Подготовка базы данных
sudo su postgres
create user fin with password '***';
create database fin;
\c fin
grant all on schema public to fin;

