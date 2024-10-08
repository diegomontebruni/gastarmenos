CREATE TABLE users (
   id UUID PRIMARY KEY,
   username VARCHAR NOT NULL UNIQUE,
   status VARCHAR NOT NULL,
   created_at TIMESTAMP WITH TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

create index idx_users_username on users (username);
create index idx_users_status on users (status);
