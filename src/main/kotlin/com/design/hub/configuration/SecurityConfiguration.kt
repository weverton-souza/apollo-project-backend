package com.design.hub.configuration

import jakarta.servlet.http.HttpServletRequest
import java.util.Locale
import java.util.Locale.LanguageRange
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val localeChangeInterceptor: LocaleChangeInterceptor
) : WebMvcConfigurer {

    companion object {
        private val logger = LoggerFactory.getLogger(SecurityConfiguration::class.java)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests()
            .anyRequest()
            .permitAll()
            .and()
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        return http.build()
    }

    override fun addInterceptors(interceptorRegistry: InterceptorRegistry) {
        logger.info("Adding localeChangeInterceptor interceptor")
        interceptorRegistry.addInterceptor(this.localeChangeInterceptor)

        logger.info("Interceptor added successfully")
    }

}
