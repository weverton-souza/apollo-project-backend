package com.design.hub.domain.security

import com.design.hub.domain.AbstractEntity
import com.design.hub.domain.user.User
import com.design.hub.enumeration.TokenType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "\"access_token\"")
@Where(clause = "deleted = false")
data class AccessToken(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    override val id: UUID = UUID.randomUUID(),

    @Column(unique = true)
    val token: String,

    @Enumerated(EnumType.STRING)
    val tokenType: TokenType = TokenType.BEARER,

    val revoked: Boolean = false,

    val expired: Boolean = false,

    val expiredAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @OneToMany(mappedBy = "refreshTokenKey.accessToken")
    val refreshTokens: List<RefreshToken> = mutableListOf()
) : AbstractEntity()
