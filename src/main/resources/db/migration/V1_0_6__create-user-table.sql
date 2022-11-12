create table users(

    id          bigserial,
    username    varchar,
    password    varchar(100),
    kyc_id      int,
    created_at  timestamp with time zone default current_timestamp,
    updated_at  timestamp with time zone,
    updated_by  varchar(100),
    deleted_at  timestamp with time zone,

    constraint pk_users primary key(id)
);