package com.design.hub.repository

import com.design.hub.domain.security.RoleDomain
import com.design.hub.enumeration.UserType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoleRepository : JpaRepository<RoleDomain, UUID> {

    @Query(
        """
        SELECT r FROM RoleDomain r WHERE r.type = :type
    """
    )
    fun findRoleDomainByType(@Param("type") type: UserType): RoleDomain
}
