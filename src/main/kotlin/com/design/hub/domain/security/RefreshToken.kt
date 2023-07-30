package com.design.hub.domain.security

import com.design.hub.domain.AbstractEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Table(name = "\"refresh_token\"")
@Where(clause = "deleted = false")
class RefreshToken(
    @EmbeddedId
    @JsonIgnore
    val refreshTokenKey: RefreshTokenKey,

    val token: String,

    var revoked: Boolean = false,

    @Column(nullable = false)
    val expiresAt: LocalDateTime
) : AbstractEntity()
