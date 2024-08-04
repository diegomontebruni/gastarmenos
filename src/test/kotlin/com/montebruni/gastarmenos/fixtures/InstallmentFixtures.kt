package com.montebruni.gastarmenos.fixtures

import com.montebruni.gastarmenos.domain.entities.Installment
import com.montebruni.gastarmenos.domain.entities.InstallmentStatus
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.InstallmentPostgresModel
import java.time.Instant
import java.util.UUID

fun createInstallment() = Installment(
    transactionId = UUID.randomUUID(),
    amount = 1000,
    dueDate = Instant.now().plusSeconds(86400),
    number = 1,
    description = "Installment",
)

fun createInstallmentPostgresModel() = InstallmentPostgresModel(
    transactionId = UUID.randomUUID(),
    amount = 1000,
    dueDate = Instant.now().plusSeconds(86400),
    number = 1,
    description = "Installment",
    status = InstallmentStatus.PENDING.name,
)
