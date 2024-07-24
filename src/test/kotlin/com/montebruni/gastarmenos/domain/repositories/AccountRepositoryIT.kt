package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.domain.entities.AccountStatus
import com.montebruni.gastarmenos.fixtures.createAccount
import com.montebruni.gastarmenos.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountRepositoryIT(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val userRepository: UserRepository
) : DatabaseIT(listOf(accountRepository, userRepository)) {

    @Test
    fun `should save account successfully`() {
        val user = createUser().also { userRepository.save(it) }
        val account = createAccount()
            .copy(userId = user.id)
            .also { accountRepository.save(it) }

        val savedAccount = accountRepository.findByIdOrNull(account.id)

        assertNotNull(savedAccount)
        assertEquals(account.userId, savedAccount?.userId)
        assertEquals(account.balance, savedAccount?.balance)
        assertEquals(account.name, savedAccount?.name)
        assertEquals(AccountStatus.ACTIVE, savedAccount?.status)
    }

    @Test
    fun `should throw error when saving account with invalid user`() {
        assertThrows<Exception> {
            accountRepository.saveAndFlush(createAccount())
        }.run {
            assertTrue(message!!.contains("violates foreign key constraint"))
        }
    }
}
