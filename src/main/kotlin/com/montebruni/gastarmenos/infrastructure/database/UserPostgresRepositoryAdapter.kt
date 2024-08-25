package com.montebruni.gastarmenos.infrastructure.database

import com.montebruni.gastarmenos.domain.entities.User
import com.montebruni.gastarmenos.domain.entities.UserStatus
import com.montebruni.gastarmenos.domain.repositories.UserRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.UserPostgresRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.UserPostgresModel
import org.springframework.stereotype.Component

@Component
class UserPostgresRepositoryAdapter(
    private val repository: UserPostgresRepository,
) : UserRepository {

    private fun toUserModel(user: User) = UserPostgresModel(
        id = user.id,
        username = user.username,
        status = user.status.name
    )

    private fun toUser(userPostgresModel: UserPostgresModel) = User(
        id = userPostgresModel.id,
        username = userPostgresModel.username,
        status = UserStatus.valueOf(userPostgresModel.status)
    )

    override fun save(user: User): User =
        toUserModel(user)
            .let(repository::save)
            .let(::toUser)

    override fun findByUsername(username: String): User? =
        repository.findByUsername(username)
            ?.let(::toUser)
}
