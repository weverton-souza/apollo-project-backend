package com.design.hub.payload.user.response

import java.util.UUID

data class UserAuthenticationResponse(
    val id: UUID,
    val name: String,
    val email: String,
    val roles: MutableList<String>
)
