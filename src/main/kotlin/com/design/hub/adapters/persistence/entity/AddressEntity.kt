package com.design.hub.adapters.persistence.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "\"address\"")
data class AddressEntity(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    @Schema(
        description = "Unique ID for the address, generated automatically",
        example = "f2eb72ca-3ba0-474c-8f87-e448a6e4f4bf"
    )
    val id: UUID? = null,

    @Column(name = "street", nullable = false, length = 100)
    @Schema(description = "Name of the street", example = "Main Street")
    val street: String,

    @Column(name = "number", nullable = false, length = 10)
    @Schema(description = "House number", example = "123")
    val number: String,

    @Column(name = "complement", length = 50)
    @Schema(description = "Address complement, if any", example = "Apartment 4B")
    val complement: String? = null,

    @Column(name = "neighborhood", nullable = false, length = 50)
    @Schema(description = "Neighborhood name", example = "Downtown")
    val neighborhood: String,

    @Column(name = "city", nullable = false, length = 50)
    @Schema(description = "City name", example = "New York")
    val city: String,

    @Column(name = "state", nullable = false, length = 50)
    @Schema(description = "State name", example = "New York")
    val state: String,

    @Column(name = "postal_code", nullable = false, length = 10)
    @Schema(description = "Postal code (ZIP code)", example = "3800")
    val postalCode: String,

    @Column(name = "verified", nullable = false)
    @Schema(description = "Indicates if the address has been verified", example = "true")
    val verified: Boolean = false
) : AbstractEntity()
