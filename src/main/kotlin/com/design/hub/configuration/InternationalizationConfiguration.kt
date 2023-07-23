package com.design.hub.configuration

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale
import java.util.Objects.isNull

@Configuration
class InternationalizationConfiguration : WebMvcConfigurer, AcceptHeaderLocaleResolver() {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(InternationalizationConfiguration::class.java)
        private const val ACCEPT_LANGUAGE = "Accept-Language"
        private val LOCALES: List<Locale> = listOf(
            Locale("pt", "BR"),
            Locale("es", "MX")
        )
    }

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("i18n/messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = SessionLocaleResolver()
        Locale.setDefault(Locale("en", "US"))
        localeResolver.defaultLocale = Locale("en")
        return localeResolver
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        return object : LocaleChangeInterceptor() {
            override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
                val acceptLanguageHeader = request.getHeader(ACCEPT_LANGUAGE)

                if (!acceptLanguageHeader.isNullOrEmpty()) {
                    val list = Locale.LanguageRange.parse(acceptLanguageHeader)
                    val locale = Locale.lookup(list, LOCALES)

                    if (!isNull(locale)) {
                        request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, locale)
                        LOGGER.debug("Locale resolved from Accept-Language header: {}", locale)
                        return true
                    }
                }
                LOGGER.debug("Locale not found in Accept-Language header. Using the default locale.")
                return true
            }
        }
    }

    override fun resolveLocale(request: HttpServletRequest): Locale {
        val language = request.getHeader(ACCEPT_LANGUAGE)
        return if (language.isNullOrEmpty()) {
            LOGGER.debug("No Accept-Language header found in the request. Using the default locale.")
            Locale.getDefault()
        } else {
            val list = Locale.LanguageRange.parse(language)
            val locale = Locale.lookup(list, LOCALES)

            return if (isNull(locale)) {
                LOGGER.debug("Locale not found in predefined locales. The Locale en_US will be used insted")
                Locale("en", "US")
            } else {
                LOGGER.debug("Resolved locale: {}", locale)
                locale
            }
        }
    }
}
