create table users(
    id bigserial primary key,
    username varchar(255) not null unique,
    email varchar(255) not null
);

create table subscriptions(
    id bigserial primary key,
    name varchar(255) not null,
    user_id bigint references users(id) on delete cascade
);