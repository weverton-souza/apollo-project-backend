package com.design.hub.domain

import com.design.hub.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.UUID

abstract class AbstractEntity(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    open val id: UUID = UUID.randomUUID(),

    @Column(name = "deleted", nullable = false)
    val deleted: Boolean = false,

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    val createdBy: User? = null,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedBy
    @Column(name = "update_by", nullable = false)
    val updatedBy: User? = null,

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
