package com.design.hub.payload.security.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request object for signing in a user")
data class SigninRequest(
    @field:Schema(description = "Email of the user", example = "johndoe@example.com")
    val email: String,

    @field:Schema(description = "Password of the user", example = "password123")
    val password: String
)
