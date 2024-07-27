package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.domain.entities.TransactionType
import com.montebruni.gastarmenos.fixtures.createAccount
import com.montebruni.gastarmenos.fixtures.createTransaction
import com.montebruni.gastarmenos.fixtures.createUser
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

class TransactionRepositoryIT(
    @Autowired private val transactionRepository: TransactionRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val userRepository: UserRepository,
) : DatabaseIT(transactionRepository) {

    @BeforeEach
    fun setup() {
        val user = createUser().let(userRepository::saveAndFlush)

        listOf(
            createAccount().copy(id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), userId = user.id),
            createAccount().copy(id = UUID.fromString("123e4567-e89b-12d3-a456-426614174001"), userId = user.id)
        ).forEach(accountRepository::saveAndFlush)
    }

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
        ).also(transactionRepository::saveAndFlush)

        val savedTransaction = transactionRepository.findByIdOrNull(transaction.id)

        assertNotNull(savedTransaction)
        assertEquals(transaction, savedTransaction)
    }

    @Test
    fun `should throw error when saving transaction with invalid credit account`() {
        assertThrows<Exception> {
            transactionRepository.saveAndFlush(createTransaction())
        }.run {
            assertTrue(message!!.contains("violates foreign key constraint"))
        }
    }

    @Test
    fun `should throw error when saving transaction with invalid debit account`() {
        val transaction = createTransaction().copy(
            type = TransactionType.DEBIT,
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
