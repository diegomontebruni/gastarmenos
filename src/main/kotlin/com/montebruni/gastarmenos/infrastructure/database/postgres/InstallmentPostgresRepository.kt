package com.montebruni.gastarmenos.infrastructure.database.postgres

import com.montebruni.gastarmenos.infrastructure.database.postgres.model.InstallmentPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface InstallmentPostgresRepository : JpaRepository<InstallmentPostgresModel, Long> {

    fun findByTransactionId(transactionId: UUID): Set<InstallmentPostgresModel>
}
