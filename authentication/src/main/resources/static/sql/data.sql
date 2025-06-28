merge into users (id, username, password) key (id) values (1, 'admin', '123456');
alter table users alter column id restart with select max(id) + 1 from users;
