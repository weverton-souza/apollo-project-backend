package com.design.hub.adapters.persistence

import com.design.hub.adapters.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<UserEntity, UUID>
