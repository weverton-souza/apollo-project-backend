package com.design.hub.service.impl

import com.design.hub.domain.security.RoleDomain
import com.design.hub.enumeration.UserType
import com.design.hub.repository.RoleRepository
import com.design.hub.service.AbstractCrudService
import com.design.hub.service.RoleService
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository
) : RoleService, AbstractCrudService<RoleDomain>(roleRepository) {
    override fun findByType(type: UserType): RoleDomain = this.roleRepository.findRoleDomainByType(type)
}
