package com.design.hub.payload.user.response

import com.design.hub.enumeration.AccountStatus
import com.design.hub.enumeration.UserType
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Response object for creating a user")
data class UserCreateResponse(
    @field:Schema(description = "Id of the user", example = "3103d1d1-f123-4473-b675-0a4591fc7035")
    val id: UUID,

    @field:Schema(description = "Name of the user", example = "John Doe")
    val name: String,

    @field:Schema(description = "Email of the user", example = "johndoe@example.com")
    val email: String,

    @field:Schema(description = "Role type of the user", example = "CUSTOMER")
    val userType: UserType,

    @field:Schema(description = "Flag indicating if the user is verified")
    val verified: Boolean,

    @field:Schema(description = "Status of the user's account")
    val status: AccountStatus
)
