package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.domain.entities.Installment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface InstallmentRepository : JpaRepository<Installment, Long> {

    fun findByTransactionId(transactionId: UUID): List<Installment>
}
