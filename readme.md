1. Применяемые технологии
Ubuntu 24.04
PostgreSQД 17.5

2. Подготовка базы данных
sudo su postgres
create user fin with password '***';
create database fin;
\c fin
grant all on schema public to fin;

3. Создание ролей
Войдите под правами администратора. Создайте пользователя.
Наделите его ресурсными ролями: flowui-filter, ui-minimal, user.
И ролью уровня строк - UserCanAccessOnlyTheirOwnDataRole. 

3. Тестирование
./gradlew test