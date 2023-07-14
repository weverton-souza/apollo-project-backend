package com.design.hub.service.impl

import com.design.hub.configuration.InternationalizationConfiguration
import com.design.hub.service.LocaleService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service

@Service
class LocaleServiceImpl(
    private val messageSource: MessageSource,
    private val interConfig: InternationalizationConfiguration
) : LocaleService {

    override fun invoke(code: String, request: HttpServletRequest): String {
        return this.messageSource.getMessage(code, null, this.interConfig.resolveLocale(request))
    }
}
