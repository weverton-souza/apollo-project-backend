package com.design.hub.resource.impl

import com.design.hub.service.RoleService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("roles")
class RoleResourceImpl(roleService: RoleService)
