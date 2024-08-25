package com.montebruni.gastarmenos.infrastructure.database.postgres

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.domain.entities.AccountStatus
import com.montebruni.gastarmenos.fixtures.createAccountPostgresModel
import com.montebruni.gastarmenos.fixtures.createUserPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class AccountPostgresRepositoryIT(
    @Autowired private val accountPostgresRepository: AccountPostgresRepository,
    @Autowired private val userPostgresRepository: UserPostgresRepository,
) : DatabaseIT(listOf(accountPostgresRepository, userPostgresRepository)) {

    @Test
    fun `should save account successfully`() {
        val user = createUserPostgresModel().also(userPostgresRepository::saveAndFlush)
        val account = createAccountPostgresModel()
            .copy(userId = user.id)
            .also(accountPostgresRepository::saveAndFlush)

        val savedAccount = accountPostgresRepository.findByIdOrNull(account.id)

        assertNotNull(savedAccount)
        assertEquals(account.userId, savedAccount?.userId)
        assertEquals(account.balance, savedAccount?.balance)
        assertEquals(account.name, savedAccount?.name)
        assertEquals(AccountStatus.ACTIVE.name, savedAccount?.status)
    }

    @Test
    fun `should throw error when saving account with invalid user`() {
        assertThrows<Exception> {
            accountPostgresRepository.saveAndFlush(createAccountPostgresModel())
        }.run {
            assertTrue(message!!.contains("violates foreign key constraint"))
        }
    }
}
