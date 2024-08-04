package com.montebruni.gastarmenos.fixtures

import com.montebruni.gastarmenos.domain.entities.User
import com.montebruni.gastarmenos.domain.entities.UserStatus
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.UserPostgresModel
import java.util.UUID

fun createUser() = User(
    username = "jon.snow@snow.north",
)

fun createUserPostgresModel() = UserPostgresModel(
    id = UUID.randomUUID(),
    username = "jon.snow@snow.north",
    status = UserStatus.ACTIVE.name,
)
