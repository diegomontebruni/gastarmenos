package com.montebruni.gastarmenos.domain.entities

import java.time.Instant
import java.util.UUID

data class Installment(
    val id: Long = 0,
    val transactionId: UUID,
    val amount: Long,
    val dueDate: Instant,
    val number: Int,
    val status: InstallmentStatus = InstallmentStatus.PENDING,
    val description: String
)
