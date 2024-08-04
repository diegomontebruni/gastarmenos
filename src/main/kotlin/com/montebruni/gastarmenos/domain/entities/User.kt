package com.montebruni.gastarmenos.domain.entities

import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val status: UserStatus = UserStatus.ACTIVE
)
