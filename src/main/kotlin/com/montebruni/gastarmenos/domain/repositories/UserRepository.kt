package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.domain.entities.User

interface UserRepository {

    fun save(user: User): User
    fun findByUsername(username: String): User?
}
