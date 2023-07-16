package com.design.hub.resource.impl

import com.design.hub.domain.user.UserDomain
import com.design.hub.exception.ResourceNotFoundException
import com.design.hub.payload.user.converter.UserConverter
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.UserCreateResponse
import com.design.hub.service.UserService
import com.design.hub.utils.I18n
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional
import java.util.UUID

@Validated
@RestController
@RequestMapping("users")
@Tag(name = "Users", description = "Resources for managing users")
class UserResourceImpl(
    private val userService: UserService,
    private val userConverter: UserConverter
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(UserResourceImpl::class.java)
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    fun create(
        @RequestBody @Valid
        entity: UserCreateRequest
    ): ResponseEntity<UserCreateResponse> {
        logger.info("[create] Creating user")

        val userDomain = this.userConverter.toCreateDomain(entity)

        val createdEntity: UserDomain = this.userService.create(userDomain)

        logger.info("[create] User created successfully: ${createdEntity.id}")
        return ResponseEntity(this.userConverter.toResponse(createdEntity), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    fun findById(@PathVariable("id") id: UUID): ResponseEntity<UserCreateResponse> {
        logger.info("[findById] Fetching entity by ID: $id")
        val optUser: Optional<UserDomain> = this.userService.findById(id)
        return if (optUser.isEmpty) {
            logger.info("[findById] Entity not found for ID: $id")
            throw ResourceNotFoundException(I18n.HTTP_4XX_404_NOT_FOUND)
        } else {
            logger.info("[findById] Entity found: ${optUser.get().id}")
            ResponseEntity(this.userConverter.toResponse(optUser.get()), HttpStatus.OK)
        }
    }

    @GetMapping
    @Operation(summary = "Get all users")
    fun findAll(pageable: Pageable): ResponseEntity<Page<UserCreateResponse>> {
        logger.info("[findAll] Fetching all entities")
        val entities = this.userService.findAll(pageable).map { this.userConverter.toResponse(it) }
        logger.info("[findAll] Total entities found: ${entities.size}")
        return ResponseEntity(entities, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID")
    fun update(@PathVariable("id") id: UUID, @RequestBody entity: UserCreateRequest): ResponseEntity<UserCreateResponse> {
        logger.info("[update] Updating entity with ID: $id")
        val userDomain: UserDomain = this.userService.update(id, this.userConverter.toCreateDomain(entity))

        return run {
            logger.info("[update] Entity updated successfully: ${userDomain.id}")
            ResponseEntity(this.userConverter.toResponse(userDomain), HttpStatus.OK)
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID")
    fun delete(@PathVariable("id") id: UUID): ResponseEntity<Unit> {
        logger.info("[delete] Deleting entity with ID: $id")
        val isDeleted = this.userService.delete(id)
        return if (isDeleted) {
            logger.info("[delete] Entity deleted successfully with ID: $id")
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            logger.info("[delete] Entity not found for ID: $id")
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
