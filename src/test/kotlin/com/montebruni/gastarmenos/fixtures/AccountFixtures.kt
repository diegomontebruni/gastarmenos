package com.montebruni.gastarmenos.fixtures

import com.montebruni.gastarmenos.domain.entities.Account
import java.util.UUID

fun createAccount() = Account(
    userId = UUID.randomUUID(),
    balance = 50.0,
    name = "Account test"
)
