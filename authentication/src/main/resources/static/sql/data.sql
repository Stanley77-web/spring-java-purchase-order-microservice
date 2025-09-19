merge into users (id, username, password) key (id) values (1, 'admin', '$2a$10$8bMR89pRAUXssRcdRuPeFuBbVcQQACQxIfMHJBTViusPu/Zg2Bngy');
alter table users alter column id restart with select max(id) + 1 from users;
