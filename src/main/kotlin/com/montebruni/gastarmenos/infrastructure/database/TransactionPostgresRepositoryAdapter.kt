package com.montebruni.gastarmenos.infrastructure.database

import com.montebruni.gastarmenos.domain.entities.Transaction
import com.montebruni.gastarmenos.domain.entities.TransactionType
import com.montebruni.gastarmenos.domain.repositories.TransactionRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.TransactionPostgresRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.TransactionPostgresModel
import org.springframework.stereotype.Component

@Component
class TransactionPostgresRepositoryAdapter(
    private val repository: TransactionPostgresRepository
) : TransactionRepository {

    private fun toModel(transaction: Transaction) =
        TransactionPostgresModel(
            id = transaction.id,
            type = transaction.type.name,
            debitAccountId = transaction.debitAccountId,
            creditAccountId = transaction.creditAccountId,
            recurrenceId = transaction.recurrenceId
        )

    private fun toTransaction(model: TransactionPostgresModel) =
        Transaction(
            id = model.id,
            type = TransactionType.valueOf(model.type),
            debitAccountId = model.debitAccountId,
            creditAccountId = model.creditAccountId,
            recurrenceId = model.recurrenceId
        )

    override fun save(transaction: Transaction) =
        toModel(transaction)
            .let(repository::save)
            .let(::toTransaction)
}
