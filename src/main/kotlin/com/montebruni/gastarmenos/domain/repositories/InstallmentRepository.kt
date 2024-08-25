package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.domain.entities.Installment
import java.util.UUID

interface InstallmentRepository {

    fun save(installment: Installment): Installment
    fun saveAll(installments: Set<Installment>): Set<Installment>
    fun findByTransactionId(transactionId: UUID): Set<Installment>
}
