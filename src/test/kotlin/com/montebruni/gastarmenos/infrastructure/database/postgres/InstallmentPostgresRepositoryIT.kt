package com.montebruni.gastarmenos.infrastructure.database.postgres

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.fixtures.createAccountPostgresModel
import com.montebruni.gastarmenos.fixtures.createInstallmentPostgresModel
import com.montebruni.gastarmenos.fixtures.createTransactionPostgresModel
import com.montebruni.gastarmenos.fixtures.createUserPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

class InstallmentPostgresRepositoryIT(
    @Autowired private val installmentRepository: InstallmentPostgresRepository,
    @Autowired private val transactionRepository: TransactionPostgresRepository,
    @Autowired private val accountRepository: AccountPostgresRepository,
    @Autowired private val userRepository: UserPostgresRepository,
) : DatabaseIT(listOf(installmentRepository, transactionRepository, accountRepository, userRepository)) {

    private val userId = UUID.randomUUID()
    private val accountId = UUID.randomUUID()
    private val transactionId = UUID.randomUUID()

    @BeforeEach
    fun setup() {
        createUserPostgresModel()
            .copy(id = userId).let(userRepository::saveAndFlush)
        createAccountPostgresModel()
            .copy(id = accountId, userId = userId).let(accountRepository::saveAndFlush)
        createTransactionPostgresModel()
            .copy(id = transactionId, creditAccountId = accountId).let(transactionRepository::saveAndFlush)
    }

    @Test
    fun `should save an installment`() {
        val installment = createInstallmentPostgresModel()
            .copy(transactionId = transactionId).let(installmentRepository::saveAndFlush)

        val savedInstallment = installmentRepository.findByIdOrNull(installment.id)

        assertNotNull(savedInstallment)
        assertEquals(installment, savedInstallment)
    }

    @Test
    fun `should save a list of installment for a transaction and find all by transactionId`() {
        val totalInstallments = 6
        for (i in 1..totalInstallments) {
            createInstallmentPostgresModel()
                .copy(transactionId = transactionId, number = i).let(installmentRepository::saveAndFlush)
        }

        val savedInstallments = installmentRepository.findByTransactionId(transactionId)

        assertEquals(totalInstallments, savedInstallments.size)
    }
}
