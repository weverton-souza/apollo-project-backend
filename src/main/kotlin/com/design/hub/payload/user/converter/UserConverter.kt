package com.design.hub.payload.user.converter

import com.design.hub.domain.user.User
import com.design.hub.enumeration.AccountStatus
import com.design.hub.enumeration.UserType
import com.design.hub.payload.AbstractConverter
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.UserCreateResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserConverter(private val passwordEncoder: PasswordEncoder) : AbstractConverter() {
    fun toCreateDomain(request: UserCreateRequest): User {
        return User(
            name = request.name!!,
            email = request.email!!,
            password = this.passwordEncoder.encode(request.password),
            type = UserType.valueOf(request.userType!!),
            status = AccountStatus.PENDING_APPROVAL,
            verified = false
        )
    }

    fun toResponse(domain: User): UserCreateResponse {
        return UserCreateResponse(
            name = domain.name,
            email = domain.email,
            password = domain.password,
            userType = domain.type,
            status = AccountStatus.PENDING_APPROVAL,
            verified = domain.verified
        )
    }
}
