package com.design.hub.security

import com.design.hub.service.SecurityService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(private val securityService: SecurityService) : OncePerRequestFilter() {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt: String = authHeader.substring(7)
        val userEmail: String = securityService.extractUserName(jwt)
        LOGGER.info("[doFilterInternal] Extracted JWT for user: $userEmail")

        if (StringUtils.isNotEmpty(userEmail) &&
            SecurityContextHolder.getContext().authentication == null
        ) {
            val userDetails: UserDetails = this.securityService.loadUserByUsername(userEmail)

            if (securityService.isTokenValid(jwt, userDetails)) {
                LOGGER.info("[doFilterInternal] Token validated for user: $userEmail")

                val context: SecurityContext = SecurityContextHolder.createEmptyContext()
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )

                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                context.authentication = authToken
                SecurityContextHolder.setContext(context)
                LOGGER.info("[doFilterInternal] User authenticated: $userEmail")
            } else {
                LOGGER.warn("[doFilterInternal] Invalid token for user: $userEmail")
            }
        }
        filterChain.doFilter(request, response)
    }
}
