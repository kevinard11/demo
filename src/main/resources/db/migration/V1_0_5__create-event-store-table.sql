create table event_store(

    "key"           uuid,
    exchange        text,
    routing_key     text,
    "message"       jsonb,
    published_at    timestamp with time zone,
    created_at      timestamp with time zone default current_timestamp,
    updated_at      timestamp with time zone,
    updated_by      varchar(100),
    deleted_at      timestamp with time zone,
    constraint pk_event_store primary key ("key")
);

create index event_store_exchange_routing_key_idx on event_store (exchange, routing_key);