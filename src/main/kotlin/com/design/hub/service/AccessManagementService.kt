package com.design.hub.service

import com.design.hub.payload.security.request.RefreshTokenRequest
import com.design.hub.payload.security.request.SigninRequest
import com.design.hub.payload.security.response.JwtAuthenticationResponse
import com.design.hub.payload.user.request.UserCreateRequest

interface AccessManagementService {
    fun signUp(request: UserCreateRequest): JwtAuthenticationResponse

    fun signIn(request: SigninRequest): JwtAuthenticationResponse

    fun refreshToken(request: RefreshTokenRequest): JwtAuthenticationResponse

    fun logout(request: RefreshTokenRequest)
}
