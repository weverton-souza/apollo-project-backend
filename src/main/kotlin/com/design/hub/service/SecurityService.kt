package com.design.hub.service

import org.springframework.security.core.userdetails.UserDetails

interface SecurityService {
    fun extractUserName(token: String): String

    fun generateToken(userDetails: UserDetails): String?

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
}
