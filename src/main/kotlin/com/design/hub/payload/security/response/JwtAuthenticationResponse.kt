package com.design.hub.payload.security.response

import com.design.hub.payload.user.response.UserAuthenticationResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response object containing the JWT token after successful authentication")
data class JwtAuthenticationResponse(
    @field:Schema(
        description = "JWT token for the authenticated session",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
            ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ" +
            ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    )
    val accessToken: String,
    val refreshToken: String,
    val expiration: Long,
    val tokenType: String,
    val profile: UserAuthenticationResponse
)
