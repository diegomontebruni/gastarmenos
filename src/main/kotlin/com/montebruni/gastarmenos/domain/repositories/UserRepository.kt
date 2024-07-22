package com.montebruni.gastarmenos.domain.repositories

import com.montebruni.gastarmenos.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID>
