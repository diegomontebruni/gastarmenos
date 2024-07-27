package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.domain.entities.TransactionType
import com.montebruni.gastarmenos.fixtures.createTransaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

class TransactionRepositoryIT(
    @Autowired private val transactionRepository: TransactionRepository
) : DatabaseIT(transactionRepository) {

    @ParameterizedTest
    @CsvSource(
        "CREDIT, 123e4567-e89b-12d3-a456-426614174000,",
        "DEBIT,,123e4567-e89b-12d3-a456-426614174000",
        "TRANSFER, 123e4567-e89b-12d3-a456-426614174000, 123e4567-e89b-12d3-a456-426614174001"
    )
    fun `should save a transaction`(type: TransactionType, creditAccount: UUID?, debitAccount: UUID?) {
        val transaction = createTransaction().copy(
            type = type,
            creditAccountId = creditAccount,
            debitAccountId = debitAccount
        ).also(transactionRepository::save)

        val savedTransaction = transactionRepository.findByIdOrNull(transaction.id)

        assertNotNull(savedTransaction)
        assertEquals(transaction, savedTransaction)
    }
}
