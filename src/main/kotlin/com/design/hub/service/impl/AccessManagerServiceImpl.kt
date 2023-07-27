package com.design.hub.service.impl

import com.design.hub.domain.user.User
import com.design.hub.enumeration.AccountStatus
import com.design.hub.enumeration.UserType
import com.design.hub.payload.user.request.SigninRequest
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.JwtAuthenticationResponse
import com.design.hub.repository.UserRepository
import com.design.hub.service.AccessManagementService
import com.design.hub.service.SecurityService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccessManagerServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val securityService: SecurityService,
    private val authenticationManager: AuthenticationManager
) : AccessManagementService {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(AccessManagerServiceImpl::class.java)
    }

    override fun signUp(request: UserCreateRequest): JwtAuthenticationResponse {
        LOGGER.info("[signUp] Signup up new user with email: ${request.email}")

        val user = User(
            name = request.name!!,
            email = request.email!!,
            password = passwordEncoder.encode(request.password),
            type = UserType.BACKOFFICE,
            status = AccountStatus.ACTIVE
        )
        userRepository.save<User>(user)
        LOGGER.info("[signUp] User created successfully with email: ${request.email}")

        val jwt: String = this.securityService.generateToken(user)
        LOGGER.info("[signUp] Token generated successfully for user with email: ${request.email}")

        return JwtAuthenticationResponse(jwt)
    }

    override fun signIn(request: SigninRequest): JwtAuthenticationResponse {
        LOGGER.info("[signIn] Signing in user with email: ${request.email}")

        this.authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))

        val user = userRepository.findByEmail(request.email)
            .orElseThrow {
                LOGGER.error("[signIn] Invalid email or password for email: ${request.email}")
                IllegalArgumentException("Invalid email or password")
            }

        val jwt: String = securityService.generateToken(user)
        LOGGER.info("[signIn] Token generated successfully for user with email: ${request.email}")

        return JwtAuthenticationResponse(jwt)
    }
}
