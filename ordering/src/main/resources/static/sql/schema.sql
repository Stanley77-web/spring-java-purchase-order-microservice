create table if not exists transaction_histories (
    id varchar(50) primary key,
    product_id varchar(50) not null,
    create_at datetime not null,
    create_by varchar(50) not null
);