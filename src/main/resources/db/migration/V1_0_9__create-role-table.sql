create table roles(

    id          bigserial,
    role        varchar(100),
    description text,
    created_at  timestamp with time zone default current_timestamp,
    updated_at  timestamp with time zone,
    updated_by  varchar(100),
    deleted_at  timestamp with time zone,

    constraint pk_roles primary key(id)
);

create table users_roles(
    user_id int,
    role_id int,

    constraint fk_users_roles_on_users foreign key (user_id) references users(id),
    constraint fk_users_roles_on_roles foreign key (role_id) references roles(id)
);