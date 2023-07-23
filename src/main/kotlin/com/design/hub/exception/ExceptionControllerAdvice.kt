package com.design.hub.exception

import com.design.hub.service.LocaleService
import com.design.hub.utils.I18n.DESIGNHUB_VALIDATION_CONSTRAINTS_NOTBLANK_DETAILS
import jakarta.servlet.ServletRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionControllerAdvice(
    private val localeService: LocaleService
) : AuthenticationFailureHandler {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception,
        servletRequest: ServletRequest,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionDetails> {
        return DesignHubErrorResponse.builder()
            .details(ex.message)
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build()
    }

    @ExceptionHandler(
        ResourceNotFoundException::class,
        UsernameNotFoundException::class
    )
    fun handleNotFoundException(
        ex: ResourceNotFoundException,
        servletRequest: ServletRequest,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionDetails> {
        return DesignHubErrorResponse
            .builder()
            .details(this.localeService.invoke(ex.message!!, request))
            .developerMessage(ex.javaClass.name)
            .status(HttpStatus.NOT_FOUND)
            .build()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest,
        webRequest: WebRequest
    ): ResponseEntity<ExceptionDetails> {
        val fieldErrors2 = ArrayList<MethodArgumentNotValidDetails>()

        val errors = mutableListOf<String>()
        val fieldErrors: List<FieldError> = ex.bindingResult.fieldErrors

        for (fieldError in fieldErrors) {
            var errorMessage: String = fieldError.defaultMessage ?: ""
            errors.add(errorMessage)

            try {
                errorMessage = this.localeService.invoke(errorMessage, request)
                fieldErrors2.add(MethodArgumentNotValidDetails(fieldError.field, errorMessage))
            } catch (ex: Exception) {
                fieldErrors2.add(MethodArgumentNotValidDetails(fieldError.field, errorMessage))
            }
        }

        return DesignHubErrorResponse
            .builder()
            .fieldErrors(fieldErrors2)
            .details(this.localeService.invoke(DESIGNHUB_VALIDATION_CONSTRAINTS_NOTBLANK_DETAILS, request))
            .developerMessage(ex.javaClass.name)
            .status(HttpStatus.BAD_REQUEST)
            .build()
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        TODO("Not yet implemented")
    }
}
