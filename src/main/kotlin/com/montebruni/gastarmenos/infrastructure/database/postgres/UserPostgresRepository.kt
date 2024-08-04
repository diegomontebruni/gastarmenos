package com.montebruni.gastarmenos.infrastructure.database.postgres

import com.montebruni.gastarmenos.infrastructure.database.postgres.model.UserPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserPostgresRepository : JpaRepository<UserPostgresModel, UUID>
