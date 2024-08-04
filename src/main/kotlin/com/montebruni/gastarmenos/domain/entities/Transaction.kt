package com.montebruni.gastarmenos.domain.entities

import java.util.UUID

data class Transaction(
    val id: UUID = UUID.randomUUID(),
    val type: TransactionType,
    val debitAccountId: UUID? = null,
    val creditAccountId: UUID? = null,
    val recurrenceId: UUID? = null,
    val installments: Set<Installment> = emptySet(),
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
