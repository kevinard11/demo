create table kycs(

    id          bigserial,
    name        text,
    email       varchar(100),
    created_at  timestamp with time zone default current_timestamp,
    updated_at  timestamp with time zone,
    updated_by  varchar(100),
    deleted_at  timestamp with time zone,

    constraint pk_kyc primary key(id)
);