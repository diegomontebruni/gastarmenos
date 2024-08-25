package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.domain.entities.Transaction

interface TransactionRepository {

    fun save(transaction: Transaction): Transaction
}
