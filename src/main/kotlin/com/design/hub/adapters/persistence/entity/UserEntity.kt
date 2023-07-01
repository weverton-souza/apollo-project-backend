package com.design.hub.adapters.persistence.entity

import com.design.hub.adapters.persistence.enumeration.AccountStatus
import com.design.hub.adapters.persistence.enumeration.UserType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "\"user\"")
data class UserEntity(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    @Schema(
        description = "Unique ID for the user, generated automatically",
        example = "f2eb72ca-3ba0-474c-8f87-e448a6e4f4bf"
    )
    val id: UUID? = null,

    @Column(name = "name", nullable = false, length = 100)
    @Schema(
        description = "User's name",
        example = "John Doe"
    )
    val name: String,

    @Column(name = "email", nullable = false, unique = true, length = 50)
    @Schema(
        description = "User's email, must be unique",
        example = "johndoe@example.com"
    )
    val email: String,

    @Column(name = "password", nullable = false, length = 64)
    @Schema(
        description = "User's password",
        example = "P@ssw0rd"
    )
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @Schema(
        description = "User's type, CUSTOMER or PROVIDER",
        example = "CUSTOMER"
    )
    val type: UserType,

    @Column(name = "country_code", nullable = false, length = 2)
    @Schema(
        description = "Country's code number",
        example = "55"
    )
    val countryCode: String,

    @Column(name = "phone_number", nullable = false, length = 15)
    @Schema(
        description = "User's DDD + phone number",
        example = "11987654321"
    )
    val phoneNumber: String,

    @OneToOne
    @JoinColumn(name = "address_id", nullable = true)
    @Schema(
        description = "User's address",
        example = "123 Main Street"
    )
    val address: AddressEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(
        description = "User account status, ACTIVE, SUSPENDED, or DELETED",
        example = "ACTIVE"
    )
    val status: AccountStatus,

    @Column(name = "verified", nullable = false)
    @Schema(
        description = "Indicates if the user's account has been verified",
        example = "true"
    )
    val verified: Boolean = false
) : AbstractEntity()
