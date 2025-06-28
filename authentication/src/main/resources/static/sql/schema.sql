create table if not exists users (
    id int auto_increment primary key,
    username varchar(50) not null unique,
    password varchar(100) not null
);

create table if not exists login_histories (
    id int auto_increment primary key,
    user_id int not null,
    login_time datetime not null,
    is_successful boolean not null,
    login_log text,
    logout_time datetime,
    foreign key (user_id) references users(id)
);