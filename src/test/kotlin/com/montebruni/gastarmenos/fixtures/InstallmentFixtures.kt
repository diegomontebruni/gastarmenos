package com.montebruni.gastarmenos.fixtures

import com.montebruni.gastarmenos.domain.entities.Installment
import java.time.Instant
import java.util.UUID

fun createInstallment() = Installment(
    transactionId = UUID.randomUUID(),
    amount = 1000,
    dueDate = Instant.now().plusSeconds(86400),
    number = 1,
    description = "First Installment",
)
