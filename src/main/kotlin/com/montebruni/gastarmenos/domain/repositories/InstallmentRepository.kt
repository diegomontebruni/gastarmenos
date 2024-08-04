package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.domain.entities.Installment
import java.util.UUID

interface InstallmentRepository {

    fun findByTransactionId(transactionId: UUID): Set<Installment>
}
