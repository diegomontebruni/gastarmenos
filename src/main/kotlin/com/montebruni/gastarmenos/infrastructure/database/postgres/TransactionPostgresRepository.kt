package com.montebruni.gastarmenos.infrastructure.database.postgres

import com.montebruni.gastarmenos.infrastructure.database.postgres.model.TransactionPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionPostgresRepository : JpaRepository<TransactionPostgresModel, UUID>