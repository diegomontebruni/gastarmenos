package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.configuration.DatabaseIT
import com.montebruni.gastarmenos.domain.entities.UserStatus
import com.montebruni.gastarmenos.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import kotlin.test.assertNotNull

class UserRepositoryIT(
    @Autowired private val userRepository: UserRepository
) : DatabaseIT(userRepository) {

    @Test
    fun `should save user successfully`() {
        val user = createUser().also { userRepository.save(it) }

        val savedUser = userRepository.findByIdOrNull(user.id)

        assertNotNull(savedUser)
        assertEquals(user.username, savedUser.username)
        assertEquals(UserStatus.ACTIVE, savedUser.status)
    }
}
