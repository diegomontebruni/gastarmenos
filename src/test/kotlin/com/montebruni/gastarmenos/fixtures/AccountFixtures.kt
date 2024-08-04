package com.montebruni.gastarmenos.fixtures

import com.montebruni.gastarmenos.domain.entities.Account
import com.montebruni.gastarmenos.domain.entities.AccountStatus
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.AccountPostgresModel
import java.util.UUID

fun createAccount() = Account(
    userId = UUID.randomUUID(),
    balance = 50.0,
    name = "Account test"
)

fun createAccountPostgresModel() = AccountPostgresModel(
    id = UUID.randomUUID(),
    userId = UUID.randomUUID(),
    status = AccountStatus.ACTIVE.name,
    balance = 50.0,
    name = "Account test"
)
