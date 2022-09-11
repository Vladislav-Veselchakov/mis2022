create table invite (
    id  bigserial not null,
    expiration_date timestamp,
    token varchar(255) UNIQUE NOT NULL,
    user_id int8 UNIQUE NOT NULL,
    primary key (id),
    FOREIGN KEY (user_id)
        REFERENCES users (id)
)
