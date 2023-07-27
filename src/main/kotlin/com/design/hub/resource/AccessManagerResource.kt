package com.design.hub.resource

import com.design.hub.exception.ExceptionDetails
import com.design.hub.payload.user.request.SigninRequest
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.JwtAuthenticationResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Access Manager", description = "Resources for managing authentication")
@ApiResponses(
    value = [
        ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionDetails::class)
                )
            ]
        )
    ]
)
interface AccessManagerResource {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "User signed up successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = JwtAuthenticationResponse::class)
                    )
                ]
            ), ApiResponse(
                responseCode = "400",
                description = "Validation Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ExceptionDetails::class)
                    )
                ]
            )
        ]
    )
    @Operation(summary = "Sign up a new user")
    fun signup(
        @Valid @RequestBody
        request: UserCreateRequest
    ): JwtAuthenticationResponse

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User signed in successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = JwtAuthenticationResponse::class)
                    )
                ]
            ), ApiResponse(
                responseCode = "400",
                description = "Invalid username or password",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ExceptionDetails::class)
                    )
                ]
            )
        ]
    )
    @Operation(summary = "Sign in a user")
    fun signin(
        @Valid @RequestBody
        request: SigninRequest
    ): JwtAuthenticationResponse
}
