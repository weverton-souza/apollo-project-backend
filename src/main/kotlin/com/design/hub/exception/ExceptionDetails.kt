package com.design.hub.exception

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Schema(description = "Custom error response for DesignHub errors")
interface ExceptionDetails {
    @get:Schema(description = "HTTP status of the error response", example = "ERROR")
    val status: HttpStatus

    @get:Schema(description = "Error details", example = "An error occurred.")
    val details: String

    @get:Schema(description = "Error message for developers", example = "InternalServerError")
    val developerMessage: String

    @get:Schema(description = "Date and time when the error occurred", example = "2023-07-16T10:49:47.150939")
    val timestamp: LocalDateTime
}
