package com.montebruni.gastarmenos.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
data class Transaction(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    val type: TransactionType,

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
) {

    init {
        when (type) {
            TransactionType.DEBIT -> require(debitAccountId != null && creditAccountId == null) {
                "Debit transactions must have a debit account and no credit account"
            }
            TransactionType.CREDIT -> require(debitAccountId == null && creditAccountId != null) {
                "Credit transactions must have a credit account and no debit account"
            }
            TransactionType.TRANSFER -> require(debitAccountId != null && creditAccountId != null) {
                "Transfer transactions must have both debit and credit accounts"
            }
        }
    }
}
