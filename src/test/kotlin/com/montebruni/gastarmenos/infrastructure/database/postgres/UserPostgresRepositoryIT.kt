package com.montebruni.gastarmenos.infrastructure.database.postgres

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.domain.entities.UserStatus
import com.montebruni.gastarmenos.fixtures.createUserPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class UserPostgresRepositoryIT(
    @Autowired private val userRepository: UserPostgresRepository
) : DatabaseIT(userRepository) {

    @Test
    fun `should save user successfully`() {
        val user = createUserPostgresModel().also { userRepository.save(it) }

        val savedUser = userRepository.findByIdOrNull(user.id)

        assertNotNull(savedUser)
        assertEquals(user.username, savedUser?.username)
        assertEquals(UserStatus.ACTIVE.name, savedUser?.status)
    }
}
