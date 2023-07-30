package com.design.hub.repository

import com.design.hub.domain.security.AccessToken
import com.design.hub.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface AccessTokenRepository : JpaRepository<AccessToken, UUID> {
    fun findByToken(token: String): Optional<AccessToken>

    fun findByUser(user: User): Optional<AccessToken>
}
