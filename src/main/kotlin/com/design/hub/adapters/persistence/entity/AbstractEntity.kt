package com.design.hub.adapters.persistence.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class AbstractEntity(
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(
        description = "Date and time of address creation",
        example = "2023-06-29T10:15:30"
    )
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Schema(
        description = "Date and time of the last address update",
        example = "2023-06-29T14:20:45"
    )
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
