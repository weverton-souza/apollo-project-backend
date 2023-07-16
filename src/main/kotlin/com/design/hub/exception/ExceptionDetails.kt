package com.design.hub.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

interface ExceptionDetails {
    val status: HttpStatus
    val details: String
    val developerMessage: String
    val timestamp: LocalDateTime
}
