package com.design.hub.repository

import com.design.hub.domain.user.UserDomain
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UserRepository : JpaRepository<UserDomain, UUID> {
    fun findByEmail(email: String): Optional<UserDomain>
}
