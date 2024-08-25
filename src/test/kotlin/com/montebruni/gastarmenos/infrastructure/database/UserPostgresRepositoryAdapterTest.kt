package com.montebruni.gastarmenos.infrastructure.database

import com.montebruni.gastarmenos.configuration.UnitTests
import com.montebruni.gastarmenos.fixtures.createUser
import com.montebruni.gastarmenos.fixtures.createUserPostgresModel
import com.montebruni.gastarmenos.infrastructure.database.postgres.UserPostgresRepository
import com.montebruni.gastarmenos.infrastructure.database.postgres.model.UserPostgresModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserPostgresRepositoryAdapterTest(
    @MockK private val repository: UserPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: UserPostgresRepositoryAdapter

    @Test
    fun `should save user`() {
        val user = createUser()
        val repositorySlot = slot<UserPostgresModel>()

        every { repository.save(capture(repositorySlot)) } answers { repositorySlot.captured }

        val response = adapter.save(user)

        assertEquals(user, response)
        assertEquals(user.id, repositorySlot.captured.id)
        assertEquals(user.username, repositorySlot.captured.username)
        assertEquals(user.status.name, repositorySlot.captured.status)

        verify(exactly = 1) { repository.save(repositorySlot.captured) }
    }

    @Test
    fun `should find user by username`() {
        val user = createUser()
        val userModel = createUserPostgresModel().copy(
            id = user.id,
            username = user.username
        )

        every { repository.findByUsername(user.username) } returns userModel

        val response = adapter.findByUsername(user.username)

        assertEquals(user, response)

        verify(exactly = 1) { repository.findByUsername(user.username) }
    }
}
