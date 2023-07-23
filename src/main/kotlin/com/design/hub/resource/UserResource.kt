package com.design.hub.resource

import com.design.hub.docs.swagger.PageUser
import com.design.hub.exception.DesignHubErrorResponse
import com.design.hub.exception.ExceptionDetails
import com.design.hub.exception.ResourceNotFoundException
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.UserCreateResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

interface UserResource {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(UserResource::class.java)
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "User Created Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserCreateResponse::class)
                    )
                ]
            ), ApiResponse(
                responseCode = "400",
                description = "Validation Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = DesignHubErrorResponse::class)
                    )
                ]
            )
        ]
    )
    @Operation(summary = "Create a new user")
    fun create(
        @RequestBody @Valid
        entity: UserCreateRequest
    ): ResponseEntity<UserCreateResponse>

    @GetMapping("/{id}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "User Created Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserCreateResponse::class)
                    )
                ]
            ), ApiResponse(
                responseCode = "400",
                description = "Validation Error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = DesignHubErrorResponse::class)
                    )
                ]
            )
        ]
    )
    @Operation(summary = "Get user by ID")
    fun findById(@PathVariable("id") id: UUID): ResponseEntity<UserCreateResponse>

    @GetMapping
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Listar todos os usuários",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PageUser::class)
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
    @Operation(summary = "Get all users")
    fun findAll(@ParameterObject pageable: Pageable): ResponseEntity<Page<UserCreateResponse>>

    @PutMapping("/{id}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User Updated Successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserCreateResponse::class)
                    )
                ]
            ), ApiResponse(
                responseCode = "404",
                description = "User Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ExceptionDetails::class)
                    )
                ]
            )
        ]
    )
    @Operation(summary = "Update user by ID")
    fun update(
        @PathVariable("id") id: UUID,
        @RequestBody entity: UserCreateRequest
    ): ResponseEntity<UserCreateResponse>

    @DeleteMapping("/{id}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "User Deleted Successfully"
            ), ApiResponse(
                responseCode = "404",
                description = "User Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ResourceNotFoundException::class)
                    )
                ]
            )
        ]
    )
    @Operation(summary = "Delete user by ID")
    fun delete(@PathVariable("id") id: UUID): ResponseEntity<Unit>
}
