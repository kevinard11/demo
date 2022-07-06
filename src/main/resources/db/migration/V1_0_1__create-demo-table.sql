create table demo(

    id          bigserial,
    demo_id     varchar,
    demo_name   varchar(100),
    description  text,
    level       int,
    active      boolean,
    version     int,
    created_at  timestamp with time zone default current_timestamp,
    updated_at  timestamp with time zone,
    updated_by  varchar(100),
    deleted_at  timestamp with time zone,

    constraint pk_demo primary key(id)
);