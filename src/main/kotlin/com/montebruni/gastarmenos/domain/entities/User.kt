package com.montebruni.gastarmenos.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "users")
data class User(

    @Id
    @Column(updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true, updatable = false)
    val username: String,

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    val status: UserStatus = UserStatus.ACTIVE,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
)
