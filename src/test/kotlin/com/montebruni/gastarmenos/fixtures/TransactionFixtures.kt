package com.montebruni.gastarmenos.fixtures

import com.montebruni.gastarmenos.domain.entities.Transaction
import com.montebruni.gastarmenos.domain.entities.TransactionType
import java.util.UUID

fun createTransaction() = Transaction(
    type = TransactionType.CREDIT,
    creditAccountId = UUID.randomUUID()
)
