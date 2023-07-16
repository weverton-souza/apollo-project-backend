package com.design.hub.payload.user.converter

import com.design.hub.domain.user.UserDomain
import com.design.hub.enumeration.AccountStatus
import com.design.hub.enumeration.UserType
import com.design.hub.payload.AbstractConverter
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.UserCreateResponse
import org.springframework.stereotype.Component

@Component
class UserConverter : AbstractConverter() {
    fun toCreateDomain(request: UserCreateRequest): UserDomain {
        return UserDomain(
            name = request.name!!,
            email = request.email!!,
            password = request.password!!,
            type = UserType.valueOf(request.userType!!),
            status = AccountStatus.PENDING_APPROVAL,
            verified = false
        )
    }

    fun toResponse(domain: UserDomain): UserCreateResponse {
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
