package com.design.hub.repository

import com.design.hub.domain.security.Token
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface TokenRepository : JpaRepository<Token, UUID> {
    fun findByToken(token: String): Optional<Token>
}
