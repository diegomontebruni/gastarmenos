package com.montebruni.gastarmenos.infrastructure.database

import com.montebruni.gastarmenos.domain.entities.Account
import com.montebruni.gastarmenos.domain.entities.AccountStatus
import com.montebruni.gastarmenos.domain.repositories.AccountRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.AccountPostgresRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.AccountPostgresModel
import org.springframework.stereotype.Component

@Component
class AccountPostgresRepositoryAdapter(
    private val repository: AccountPostgresRepository
) : AccountRepository {

    private fun toAccountModel(account: Account) = AccountPostgresModel(
        id = account.id,
        userId = account.userId,
        status = account.status.name,
        balance = account.balance,
        name = account.name
    )

    private fun toAccount(accountPostgresModel: AccountPostgresModel) = Account(
        id = accountPostgresModel.id,
        userId = accountPostgresModel.userId,
        status = AccountStatus.valueOf(accountPostgresModel.status),
        balance = accountPostgresModel.balance,
        name = accountPostgresModel.name
    )

    override fun save(account: Account): Account =
        toAccountModel(account)
            .let(repository::save)
            .let(::toAccount)
}
