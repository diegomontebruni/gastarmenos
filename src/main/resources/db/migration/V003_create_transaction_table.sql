CREATE TABLE transaction (
    id UUID PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    debit_account_id UUID,
    credit_account_id UUID,
    recurrenceId: UUID,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_debit_account FOREIGN KEY (debit_account_id) REFERENCES account(id),
    CONSTRAINT fk_credit_account FOREIGN KEY (credit_account_id) REFERENCES account(id)
);
