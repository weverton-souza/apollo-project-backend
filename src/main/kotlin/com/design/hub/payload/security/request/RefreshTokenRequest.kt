package com.design.hub.payload.security.request

import java.io.Serializable

data class RefreshTokenRequest(
    val refreshToken: String
) : Serializable
