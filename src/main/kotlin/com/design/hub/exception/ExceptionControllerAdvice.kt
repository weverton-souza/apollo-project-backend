package com.design.hub.exception

import com.design.hub.service.LocaleService
import com.design.hub.utils.I18n
import jakarta.servlet.ServletRequest
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice(private val localeService: LocaleService) {

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception,
        servletRequest: ServletRequest,
        request: HttpServletRequest
    ): ResponseEntity<DesignHubErrorResponse> {
        val errorMsg = this.localeService.invoke(I18n.HTTP_5XX_500_INTERNAL_SERVER_ERROR, request)

        return ResponseEntity(
            DesignHubErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFoundException(
        ex: ResourceNotFoundException,
        servletRequest: ServletRequest,
        request: HttpServletRequest
    ): ResponseEntity<DesignHubErrorResponse> {
        val errorMsg = this.localeService.invoke(I18n.HTTP_4XX_404_NOT_FOUND, request)

        return ResponseEntity(
            DesignHubErrorResponse(HttpStatus.NOT_FOUND, errorMsg),
            HttpStatus.NOT_FOUND
        )
    }
}
