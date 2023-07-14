package com.design.hub.payload.user.request

import com.design.hub.enumeration.UserType
import com.design.hub.payload.PayloadRequest
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request object for creating a user")
class UserCreateRequest(
    @field:Schema(description = "Name of the user", maxLength = 100, example = "John Doe")
    val name: String,

    @field:Schema(description = "Email of the user", maxLength = 50, example = "johndoe@example.com")
    val email: String,

    @field:Schema(description = "Password of the user", maxLength = 64, example = "password123")
    val password: String,

    @field:Schema(description = "Role type of the user", example = "CUSTOMER")
    val userType: UserType
) : PayloadRequest
