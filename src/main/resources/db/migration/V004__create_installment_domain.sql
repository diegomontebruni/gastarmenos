CREATE TABLE installment (
    id INTEGER PRIMARY KEY,
    transaction_id UUID NOT NULL,
    amount INTEGER NOT NULL,
    due_date TIMESTAMP WITH TIME ZONE NOT NULL,
    number INT NOT NULL,
    status VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_installment_transaction FOREIGN KEY (transaction_id) REFERENCES transaction(id)
);
