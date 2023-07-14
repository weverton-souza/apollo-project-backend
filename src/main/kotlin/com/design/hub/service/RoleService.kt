package com.design.hub.service

import com.design.hub.domain.security.RoleDomain
import com.design.hub.enumeration.UserType

interface RoleService : DesignHubService<RoleDomain> {
    fun findByType(type: UserType): RoleDomain
}
