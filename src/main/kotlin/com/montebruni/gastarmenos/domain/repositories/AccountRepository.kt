package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.domain.entities.Account

interface AccountRepository {

    fun save(account: Account): Account
}
