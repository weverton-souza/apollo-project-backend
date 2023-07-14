package com.design.hub.repository

import com.design.hub.domain.user.UserDomain
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<UserDomain, UUID>
