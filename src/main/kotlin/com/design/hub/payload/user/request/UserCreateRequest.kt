package com.design.hub.payload.user.request

import com.design.hub.annotation.ValueOfEnum
import com.design.hub.enumeration.UserType
import com.design.hub.payload.PayloadRequest
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request object for creating a user")
data class UserCreateRequest(

    @field:Size(max = 50)
    @field:NotBlank(message = "designhub.validation.constraints.NotBlank.field.message")
    @field:Schema(description = "User name", maxLength = 100, example = "John Doe", required = true)
    val name: String?,

    @field:Email
    @field:Size(max = 50)
    @field:NotBlank(message = "designhub.validation.constraints.NotBlank.field.message")
    @field:Schema(description = "User email", maxLength = 50, example = "johndoe@example.com", required = true)
    val email: String?,

    @field:Size(max = 64)
    @field:NotBlank(message = "designhub.validation.constraints.NotBlank.field.message")
    @field:Schema(description = "User password", maxLength = 64, example = "password123", required = true)
    val password: String?,

    @Enumerated(EnumType.STRING)
    @field:ValueOfEnum(
        enumClass = UserType::class,
        name = "userType",
        values = "'BACKOFFICE', 'CUSTOMER', 'PROVIDER'"
    )
    @field:Schema(description = "User type", example = "CUSTOMER", required = true)
    var userType: String?
) : PayloadRequest
