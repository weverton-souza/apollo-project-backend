package com.design.hub.repository

import com.design.hub.domain.security.RefreshToken
import com.design.hub.domain.security.RefreshTokenKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, RefreshTokenKey> {
    fun findByRefreshTokenKeyAccessTokenToken(token: String): Optional<RefreshToken>

    fun findByToken(refreshToken: String): Optional<RefreshToken>
}
