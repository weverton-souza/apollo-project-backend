package com.design.hub.service.impl

import com.design.hub.domain.user.UserDomain
import com.design.hub.repository.UserRepository
import com.design.hub.service.AbstractCrudService
import com.design.hub.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    userRepository: UserRepository
) : UserService, AbstractCrudService<UserDomain>(userRepository)
