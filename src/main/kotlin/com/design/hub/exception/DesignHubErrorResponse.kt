package com.design.hub.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

data class DesignHubErrorResponse(
    override val status: HttpStatus,
    override val details: String,
    override val developerMessage: String,
    override val timestamp: LocalDateTime,
    val fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null
) : ExceptionDetails {

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    open class Builder {
        companion object {
            val LOGGER: Logger = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)
        }

        private var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        private var details: String = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
        private var developerMessage: String = ""
        private var timestamp: LocalDateTime = LocalDateTime.now()
        private var fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null

        fun status(status: HttpStatus): Builder {
            this.status = status
            return this
        }

        fun details(details: String?): Builder {
            if (details != null) {
                this.details = details
            }
            return this
        }

        fun developerMessage(developerMessage: String): Builder {
            this.developerMessage = developerMessage
            return this
        }

        fun timestamp(timestamp: LocalDateTime): Builder {
            this.timestamp = timestamp
            return this
        }

        fun fieldErrors(fieldErrors: MutableCollection<MethodArgumentNotValidDetails>): Builder {
            this.fieldErrors = fieldErrors
            return this
        }

        fun build(): ResponseEntity<ExceptionDetails> {
            return this.buildExceptionMessage(
                this.status,
                this.details,
                this.developerMessage,
                this.timestamp,
                this.fieldErrors
            )
        }

        private fun buildExceptionMessage(
            status: HttpStatus,
            details: String,
            developerMessage: String,
            timestamp: LocalDateTime,
            fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null
        ): ResponseEntity<ExceptionDetails> {
            return try {
                LOGGER.info(
                    """
                        [buildExceptionMessage] Message Error: 
                        status: $status, 
                        details: $details, developerMessage: $developerMessage, 
                        timestamp: $timestamp, status: $status
                    """.trimIndent()
                )

                ResponseEntity(
                    DesignHubErrorResponse(status, details, developerMessage, timestamp, fieldErrors),
                    status
                )
            } catch (ex: Exception) {
                LOGGER.info(
                    """
                        [buildExceptionMessage] Message not found: 
                        status: $status, 
                        details: $details, developerMessage: $developerMessage, 
                        timestamp: $timestamp, status: $status
                    """.trimIndent()
                )
                ResponseEntity(
                    DesignHubErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        details,
                        developerMessage,
                        timestamp,
                        fieldErrors
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
            }
        }
    }
}
