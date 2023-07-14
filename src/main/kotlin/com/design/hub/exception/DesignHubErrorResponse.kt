package com.design.hub.exception

import org.springframework.http.HttpStatus

class DesignHubErrorResponse(
    val status: HttpStatus,
    val message: String
)
