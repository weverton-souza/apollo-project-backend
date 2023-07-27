package com.design.hub.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

interface SecurityService : UserDetailsService {
    fun extractUserName(token: String): String

    fun generateToken(userDetails: UserDetails): String

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
}
