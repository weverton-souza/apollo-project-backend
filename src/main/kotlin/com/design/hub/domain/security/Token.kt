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
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import java.util.UUID

@Entity
@Table(name = "\"token\"")
@Where(clause = "deleted = false")
data class Token(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    override val id: UUID? = null,

    @Column(unique = true)
    val token: String,

    @Enumerated(EnumType.STRING)
    var tokenType: TokenType = TokenType.BEARER,

    var revoked: Boolean = false,

    var expired: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User
) : AbstractEntity()
