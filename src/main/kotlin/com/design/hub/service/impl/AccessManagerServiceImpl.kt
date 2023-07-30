package com.design.hub.service.impl

import com.design.hub.configuration.SecurityProperties
import com.design.hub.domain.user.User
import com.design.hub.enumeration.AccountStatus
import com.design.hub.enumeration.UserType
import com.design.hub.payload.security.request.RefreshTokenRequest
import com.design.hub.payload.security.request.SigninRequest
import com.design.hub.payload.security.response.JwtAuthenticationResponse
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.UserAuthenticationResponse
import com.design.hub.repository.RefreshTokenRepository
import com.design.hub.repository.UserRepository
import com.design.hub.service.AccessManagementService
import com.design.hub.service.SecurityService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AccessManagerServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val securityService: SecurityService,
    private val authenticationManager: AuthenticationManager,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val securityProperties: SecurityProperties
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

        val accessToken: String = this.securityService.generateToken(user)
        LOGGER.info("[signUp] Token generated successfully for user with email: ${request.email}")

        return this.buildJwtAuthenticationResponse(accessToken, user)
    }

    override fun signIn(request: SigninRequest): JwtAuthenticationResponse {
        LOGGER.info("[signIn] Signing in user with email: ${request.email}")

        this.authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))

        val user = userRepository.findByEmail(request.email)
            .orElseThrow {
                LOGGER.error("[signIn] Invalid email or password for email: ${request.email}")
                IllegalArgumentException("Invalid email or password")
            }

        val accessToken: String = securityService.generateToken(user)
        LOGGER.info("[signIn] Token generated successfully for user with email: ${request.email}")

        return this.buildJwtAuthenticationResponse(accessToken, user)
    }

    override fun refreshToken(request: RefreshTokenRequest): JwtAuthenticationResponse {
        LOGGER.info("[refreshToken] Refreshing token for user with email: ${request.refreshToken}")

        val refreshToken = this.refreshTokenRepository.findByToken(request.refreshToken)
            .orElseThrow {
                IllegalArgumentException("Refresh token not found")
            }

        val user = refreshToken.refreshTokenKey.accessToken.user

        if (refreshToken.expiresAt.isBefore(LocalDateTime.now())) {
            LOGGER.error("[refreshToken] Refresh token expired for user with email: ${request.refreshToken}")
            throw IllegalArgumentException("Refresh token expired")
        }

        val accessToken: String = this.securityService.generateToken(user)
        LOGGER.info("[refreshToken] Token generated successfully for user with email: ${request.refreshToken}")

        return this.buildJwtAuthenticationResponse(accessToken, user)
    }

    override fun logout(request: RefreshTokenRequest) {
        LOGGER.info("[logout] Logging out user with email: ${request.refreshToken}")

        val refreshToken = this.refreshTokenRepository.findByToken(request.refreshToken)
            .orElseThrow {
                IllegalArgumentException("Refresh token not found")
            }

        refreshToken.revoked = true
        this.refreshTokenRepository.save(refreshToken)
        LOGGER.info("[logout] Refresh token updated successfully for user with email: ${request.refreshToken}")
    }

    private fun buildJwtAuthenticationResponse(accessToken: String, user: User): JwtAuthenticationResponse {
        val refreshToken = this.refreshTokenRepository.findByRefreshTokenKeyAccessTokenToken(accessToken)
            .orElseThrow {
                IllegalArgumentException("Refresh token not found")
            }

        LOGGER.info("[buildJwtAuthenticationResponse] Token generated successfully for user with email: ${user.email}")

        return JwtAuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken.token,
            tokenType = "Bearer",
            expiration = this.securityProperties.refreshTokenExpiration,
            profile = UserAuthenticationResponse(
                id = user.id,
                name = user.name,
                email = user.email,
                roles = user.authoritiesAsStringList()
            )
        )
    }
}
