package com.montebruni.gastarmenos.domain.entities

import com.montebruni.gastarmenos.fixtures.createTransaction
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.UUID

class TransactionTest {

    @ParameterizedTest
    @CsvSource(
        "CREDIT, 123e4567-e89b-12d3-a456-426614174000,",
        "DEBIT,,123e4567-e89b-12d3-a456-426614174000",
        "TRANSFER, 123e4567-e89b-12d3-a456-426614174000, 123e4567-e89b-12d3-a456-426614174001"
    )
    fun `should create transaction with valid credit or debit account`(
        type: TransactionType,
        creditAccount: UUID? = null,
        debitAccount: UUID? = null
    ) {
        assertDoesNotThrow {
            createTransaction().copy(
                type = type,
                creditAccountId = creditAccount,
                debitAccountId = debitAccount
            )
        }
    }

    @ParameterizedTest
    @CsvSource(
        "CREDIT,, 123e4567-e89b-12d3-a456-426614174000",
        "DEBIT, 123e4567-e89b-12d3-a456-426614174000,",
        "TRANSFER,,"
    )
    fun `should throw error when try to create transaction with invalid credit or debit account`(
        type: TransactionType,
        creditAccount: UUID? = null,
        debitAccount: UUID? = null
    ) {
        assertThrows<IllegalArgumentException> {
            createTransaction().copy(
                type = type,
                creditAccountId = creditAccount,
                debitAccountId = debitAccount
            )
        }
    }
}
