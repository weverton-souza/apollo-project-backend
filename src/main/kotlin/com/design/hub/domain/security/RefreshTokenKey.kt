package com.design.hub.domain.security

import com.design.hub.domain.user.User
import jakarta.persistence.Embeddable
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.io.Serializable

@Embeddable
data class RefreshTokenKey(
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToOne
    @JoinColumn(name = "access_token_id")
    val accessToken: AccessToken
) : Serializable
