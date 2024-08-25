package com.montebruni.gastarmenos.infrastructure.database

import com.montebruni.gastarmenos.configuration.UnitTests
import com.montebruni.gastarmenos.fixtures.createInstallment
import com.montebruni.gastarmenos.fixtures.createInstallmentPostgresModel
import com.montebruni.gastarmenos.infrastructure.database.postgres.InstallmentPostgresRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.InstallmentPostgresModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InstallmentPostgresRepositoryAdapterTest(
    @MockK private val repository: InstallmentPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: InstallmentPostgresRepositoryAdapter

    @Test
    fun `should save an installment`() {
        val installment = createInstallment()
        val repositorySlot = slot<InstallmentPostgresModel>()

        every { repository.save(capture(repositorySlot)) } answers { repositorySlot.captured }

        val result = adapter.save(installment)

        assertThat(result).isEqualTo(installment)
        assertEquals(installment.id, repositorySlot.captured.id)
        assertEquals(installment.transactionId, repositorySlot.captured.transactionId)
        assertEquals(installment.amount, repositorySlot.captured.amount)
        assertEquals(installment.dueDate, repositorySlot.captured.dueDate)
        assertEquals(installment.number, repositorySlot.captured.number)
        assertEquals(installment.status.name, repositorySlot.captured.status)
        assertEquals(installment.description, repositorySlot.captured.description)

        verify(exactly = 1) { repository.save(repositorySlot.captured) }
    }

    @Test
    fun `should save all installments`() {
        val installments = setOf(createInstallment(), createInstallment())
        val repositorySlot = slot<List<InstallmentPostgresModel>>()

        every { repository.saveAll(capture(repositorySlot)) } answers { repositorySlot.captured }

        val result = adapter.saveAll(installments)

        assertThat(result).containsExactlyElementsOf(installments)

        verify(exactly = 1) { repository.saveAll(repositorySlot.captured) }
    }

    @Test
    fun `should find installments by transaction id`() {
        val transactionId = createInstallment().transactionId
        val installments = setOf(createInstallmentPostgresModel(), createInstallmentPostgresModel())

        every { repository.findByTransactionId(transactionId) } returns installments

        val result = adapter.findByTransactionId(transactionId)

        assertEquals(installments.size, result.size)

        val firstInstallment = installments.first()
        val firstResult = result.first()
        assertEquals(firstInstallment.id, firstResult.id)
        assertEquals(firstInstallment.transactionId, firstResult.transactionId)
        assertEquals(firstInstallment.amount, firstResult.amount)
        assertEquals(firstInstallment.dueDate, firstResult.dueDate)
        assertEquals(firstInstallment.number, firstResult.number)
        assertEquals(firstInstallment.status, firstResult.status.name)
        assertEquals(firstInstallment.description, firstResult.description)

        val lastInstallment = installments.last()
        val lastResult = result.last()
        assertEquals(lastInstallment.id, lastResult.id)
        assertEquals(lastInstallment.transactionId, lastResult.transactionId)
        assertEquals(lastInstallment.amount, lastResult.amount)
        assertEquals(lastInstallment.dueDate, lastResult.dueDate)
        assertEquals(lastInstallment.number, lastResult.number)
        assertEquals(lastInstallment.status, lastResult.status.name)
        assertEquals(lastInstallment.description, lastResult.description)

        verify(exactly = 1) { repository.findByTransactionId(transactionId) }
    }
}
