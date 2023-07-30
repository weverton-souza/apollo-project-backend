package com.design.hub.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "application.design-hub.global.security")
data class SecurityProperties @ConstructorBinding constructor(
    val tokenExpiration: Long,
    val refreshTokenExpiration: Long,
    val jwtSigningKey: String
)
