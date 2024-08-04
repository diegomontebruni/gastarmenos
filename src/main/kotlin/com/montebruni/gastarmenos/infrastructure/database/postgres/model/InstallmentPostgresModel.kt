package com.montebruni.gastarmenos.infrastructure.database.postgres.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "installment")
data class InstallmentPostgresModel(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(name = "transaction_id")
    val transactionId: UUID,

    @Column(name = "amount")
    val amount: Long,

    @Column(name = "due_date")
    val dueDate: Instant,

    @Column(name = "number")
    val number: Int,

    @Column(name = "status")
    val status: String,

    @Column(name = "description")
    val description: String,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now(),
)
