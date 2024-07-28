package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.fixtures.createAccount
import com.montebruni.gastarmenos.fixtures.createInstallment
import com.montebruni.gastarmenos.fixtures.createTransaction
import com.montebruni.gastarmenos.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

class InstallmentRepositoryIT(
    @Autowired private val installmentRepository: InstallmentRepository,
    @Autowired private val transactionRepository: TransactionRepository,
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val userRepository: UserRepository,
) : DatabaseIT(listOf(installmentRepository, transactionRepository, accountRepository, userRepository)) {

    private val userId = UUID.randomUUID()
    private val accountId = UUID.randomUUID()
    private val transactionId = UUID.randomUUID()

    @BeforeEach
    fun setup() {
        createUser()
            .copy(id = userId).let(userRepository::saveAndFlush)
        createAccount()
            .copy(id = accountId, userId = userId).let(accountRepository::saveAndFlush)
        createTransaction()
            .copy(id = transactionId, creditAccountId = accountId).let(transactionRepository::saveAndFlush)
    }

    @Test
    fun `should save an installment`() {
        val installment = createInstallment()
            .copy(transactionId = transactionId).let(installmentRepository::saveAndFlush)

        val savedInstallment = installmentRepository.findByIdOrNull(installment.id)

        assertNotNull(savedInstallment)
        assertEquals(installment, savedInstallment)
    }

    @Test
    fun `should save a list of installment for a transaction and find all by transactionId`() {
        for (i in 1..6) {
            createInstallment()
                .copy(transactionId = transactionId, number = i).let(installmentRepository::saveAndFlush)
        }

        val savedInstallments = installmentRepository.findByTransactionId(transactionId)

        assertEquals(6, savedInstallments.size)
    }
}
