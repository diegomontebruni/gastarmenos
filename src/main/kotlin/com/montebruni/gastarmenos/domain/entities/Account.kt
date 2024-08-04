package com.montebruni.gastarmenos.domain.entities

import java.util.UUID

data class Account(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val status: AccountStatus = AccountStatus.ACTIVE,
    val balance: Double,
    val name: String
)
