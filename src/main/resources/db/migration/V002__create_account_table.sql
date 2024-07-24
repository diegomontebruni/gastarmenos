CREATE TABLE account (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    status VARCHAR NOT NULL,
    balance DECIMAL(19, 2) NOT NULL,
    name VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users(id)
);

create index idx_account_user_id on account (user_id);
create index idx_account_status on account (status);
