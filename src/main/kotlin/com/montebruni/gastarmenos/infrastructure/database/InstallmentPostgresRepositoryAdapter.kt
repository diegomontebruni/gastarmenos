package com.montebruni.gastarmenos.infrastructure.database

import com.montebruni.gastarmenos.domain.entities.Installment
import com.montebruni.gastarmenos.domain.entities.InstallmentStatus
import com.montebruni.gastarmenos.domain.repositories.InstallmentRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.InstallmentPostgresRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.InstallmentPostgresModel
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class InstallmentPostgresRepositoryAdapter(
    private val repository: InstallmentPostgresRepository
) : InstallmentRepository {

    private fun toModel(installment: Installment) =
        InstallmentPostgresModel(
            id = installment.id,
            transactionId = installment.transactionId,
            amount = installment.amount,
            dueDate = installment.dueDate,
            number = installment.number,
            status = installment.status.name,
            description = installment.description
        )

    private fun toInstallment(model: InstallmentPostgresModel) =
        Installment(
            id = model.id,
            transactionId = model.transactionId,
            amount = model.amount,
            dueDate = model.dueDate,
            number = model.number,
            status = InstallmentStatus.valueOf(model.status),
            description = model.description
        )

    override fun save(installment: Installment) =
        toModel(installment)
            .let(repository::save)
            .let(::toInstallment)

    override fun saveAll(installments: Set<Installment>): Set<Installment> =
        installments
            .map(::toModel)
            .let(repository::saveAll)
            .map(::toInstallment)
            .toSet()

    override fun findByTransactionId(transactionId: UUID): Set<Installment> =
        repository
            .findByTransactionId(transactionId)
            .map(::toInstallment)
            .toSet()
}
