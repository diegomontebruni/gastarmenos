package com.montebruni.gastarmenos.infrastructure.database

import com.montebruni.gastarmenos.configuration.UnitTests
import com.montebruni.gastarmenos.fixtures.createAccount
import com.montebruni.gastarmenos.infrastructure.database.postgres.AccountPostgresRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.AccountPostgresModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AccountPostgresRepositoryAdapterTest(
    @MockK private val repository: AccountPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: AccountPostgresRepositoryAdapter

    @Test
    fun `should save account`() {
        val account = createAccount()
        val repositorySlot = slot<AccountPostgresModel>()

        every { repository.save(capture(repositorySlot)) } answers { repositorySlot.captured }

        val result = adapter.save(account)

        assertEquals(account, result)

        verify(exactly = 1) { repository.save(repositorySlot.captured) }
    }
}
