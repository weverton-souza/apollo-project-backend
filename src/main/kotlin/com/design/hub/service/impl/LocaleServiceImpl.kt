package com.design.hub.service.impl

import com.design.hub.configuration.InternationalizationConfiguration
import com.design.hub.service.LocaleService
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service

@Service
class LocaleServiceImpl(
    private val messageSource: MessageSource,
    private val interConfig: InternationalizationConfiguration
) : LocaleService {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(LocaleServiceImpl::class.java)
    }

    override fun invoke(code: String, request: HttpServletRequest): String {
        LOGGER.info("Fetching message for code: $code and request locale: ${interConfig.resolveLocale(request)}")
        return this.messageSource.getMessage(code, null, this.interConfig.resolveLocale(request)).also {
            LOGGER.info("Fetched message: $it for code: $code")
        }
    }
}
