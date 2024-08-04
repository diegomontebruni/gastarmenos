package com.montebruni.gastarmenos.infrastructure.database.postgres.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "transaction")
data class TransactionPostgresModel(

    @Id
    val id: UUID,

    val type: String,

    @Column(name = "debit_account_id")
    val debitAccountId: UUID? = null,

    @Column(name = "credit_account_id")
    val creditAccountId: UUID? = null,

    @Column(name = "recurrence_id")
    val recurrenceId: UUID? = null,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
)
