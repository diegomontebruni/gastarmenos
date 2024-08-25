package com.montebruni.gastarmenos.infrastructure.database

import com.montebruni.gastarmenos.configuration.UnitTests
import com.montebruni.gastarmenos.fixtures.createTransaction
import com.montebruni.gastarmenos.infrastructure.database.postgres.TransactionPostgresRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.TransactionPostgresModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TransactionPostgresRepositoryAdapterTest(
    @MockK private val repository: TransactionPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: TransactionPostgresRepositoryAdapter

    @Test
    fun `should save a transaction`() {
        val transaction = createTransaction()
        val repositorySlot = slot<TransactionPostgresModel>()

        every { repository.save(capture(repositorySlot)) } answers { repositorySlot.captured }

        val result = adapter.save(transaction)

        assertEquals(transaction, result)
        assertEquals(transaction.id, repositorySlot.captured.id)
        assertEquals(transaction.type.name, repositorySlot.captured.type)
        assertEquals(transaction.debitAccountId, repositorySlot.captured.debitAccountId)
        assertEquals(transaction.creditAccountId, repositorySlot.captured.creditAccountId)
        assertEquals(transaction.recurrenceId, repositorySlot.captured.recurrenceId)

        verify(exactly = 1) { repository.save(repositorySlot.captured) }
    }
}
