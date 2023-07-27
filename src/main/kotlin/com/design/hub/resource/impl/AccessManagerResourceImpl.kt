package com.design.hub.resource.impl

import com.design.hub.payload.user.request.SigninRequest
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.JwtAuthenticationResponse
import com.design.hub.resource.AccessManagerResource
import com.design.hub.service.AccessManagementService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping
class AccessManagerResourceImpl(
    private val accessManagementService: AccessManagementService
) : AccessManagerResource {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(AccessManagerResourceImpl::class.java)
    }

    @PostMapping("/signup")
    override fun signup(
        @RequestBody @Valid
        request: UserCreateRequest
    ): JwtAuthenticationResponse {
        LOGGER.info("[signup] Attempting to sign up user")

        val jwtResponse: JwtAuthenticationResponse = this.accessManagementService.signUp(request)
        LOGGER.info("[signup] User signed up successfully")

        return jwtResponse
    }

    @PostMapping("/signin")
    override fun signin(
        @RequestBody @Valid
        request: SigninRequest
    ): JwtAuthenticationResponse {
        LOGGER.info("[signin] Attempting to sign in user")

        val jwtResponse: JwtAuthenticationResponse = this.accessManagementService.signIn(request)
        LOGGER.info("[signin] User signed in successfully")

        return jwtResponse
    }
}
