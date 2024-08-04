package com.montebruni.gastarmenos.infrastructure.database.postgres

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.domain.entities.TransactionType
import com.montebruni.gastarmenos.fixtures.createAccountPostgresModel
import com.montebruni.gastarmenos.fixtures.createTransactionPostgresModel
import com.montebruni.gastarmenos.fixtures.createUserPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

class TransactionPostgresRepositoryIT(
    @Autowired private val transactionRepository: TransactionPostgresRepository,
    @Autowired private val accountRepository: AccountPostgresRepository,
    @Autowired private val userRepository: UserPostgresRepository,
) : DatabaseIT(listOf(transactionRepository, accountRepository, userRepository)) {

    @BeforeEach
    fun setup() {
        val user = createUserPostgresModel().let(userRepository::saveAndFlush)

        listOf(
            createAccountPostgresModel()
                .copy(id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), userId = user.id),
            createAccountPostgresModel()
                .copy(id = UUID.fromString("123e4567-e89b-12d3-a456-426614174001"), userId = user.id)
        ).forEach(accountRepository::saveAndFlush)
    }

    @ParameterizedTest
    @CsvSource(
        "CREDIT, 123e4567-e89b-12d3-a456-426614174000,",
        "DEBIT,,123e4567-e89b-12d3-a456-426614174000",
        "TRANSFER, 123e4567-e89b-12d3-a456-426614174000, 123e4567-e89b-12d3-a456-426614174001"
    )
    fun `should save a transaction`(type: String, creditAccount: UUID?, debitAccount: UUID?) {
        val transaction = createTransactionPostgresModel().copy(
            type = type,
            creditAccountId = creditAccount,
            debitAccountId = debitAccount
        ).also(transactionRepository::saveAndFlush)

        val savedTransaction = transactionRepository.findByIdOrNull(transaction.id)

        assertNotNull(savedTransaction)
        assertEquals(transaction, savedTransaction)
    }

    @Test
    fun `should throw error when saving transaction with invalid credit account`() {
        assertThrows<Exception> {
            transactionRepository.saveAndFlush(createTransactionPostgresModel())
        }.run {
            assertTrue(message!!.contains("violates foreign key constraint"))
        }
    }

    @Test
    fun `should throw error when saving transaction with invalid debit account`() {
        val transaction = createTransactionPostgresModel().copy(
            type = TransactionType.DEBIT.name,
            debitAccountId = UUID.randomUUID(),
            creditAccountId = null
        )

        assertThrows<Exception> {
            transactionRepository.saveAndFlush(transaction)
        }.run {
            assertTrue(message!!.contains("violates foreign key constraint"))
        }
    }
}
