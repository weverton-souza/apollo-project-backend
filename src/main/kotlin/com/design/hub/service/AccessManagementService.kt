package com.design.hub.service

import com.design.hub.payload.user.request.SigninRequest
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.JwtAuthenticationResponse

interface AccessManagementService {
    fun signUp(request: UserCreateRequest): JwtAuthenticationResponse

    fun signIn(request: SigninRequest): JwtAuthenticationResponse
}
