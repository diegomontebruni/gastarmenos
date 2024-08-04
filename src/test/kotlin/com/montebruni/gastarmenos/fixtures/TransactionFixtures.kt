package com.montebruni.gastarmenos.fixtures

import com.montebruni.gastarmenos.domain.entities.Transaction
import com.montebruni.gastarmenos.domain.entities.TransactionType
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.TransactionPostgresModel
import java.util.UUID

fun createTransaction() = Transaction(
    type = TransactionType.CREDIT,
    creditAccountId = UUID.randomUUID()
)

fun createTransactionPostgresModel() = TransactionPostgresModel(
    id = UUID.randomUUID(),
    type = TransactionType.CREDIT.name,
    creditAccountId = UUID.randomUUID(),
)
