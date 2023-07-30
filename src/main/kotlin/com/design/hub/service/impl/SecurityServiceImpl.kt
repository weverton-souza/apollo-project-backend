package com.design.hub.service.impl

import com.design.hub.configuration.SecurityProperties
import com.design.hub.domain.security.AccessToken
import com.design.hub.domain.security.RefreshToken
import com.design.hub.domain.security.RefreshTokenKey
import com.design.hub.repository.AccessTokenRepository
import com.design.hub.repository.RefreshTokenRepository
import com.design.hub.repository.UserRepository
import com.design.hub.service.SecurityService
import com.design.hub.utils.I18n
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.security.Key
import java.security.SecureRandom
import java.time.LocalDateTime
import java.util.Base64
import java.util.Date
import java.util.UUID

@Service
class SecurityServiceImpl(
    private val userRepository: UserRepository,
    private val securityProperties: SecurityProperties,
    private val accessTokenRepository: AccessTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) : SecurityService, UserDetailsService {

    override fun extractUserName(token: String): String = extractClaim<String>(token, Claims::getSubject)

    override fun generateToken(userDetails: UserDetails): String = generateToken(HashMap(), userDetails)

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean =
        extractUserName(token) == userDetails.username && !isTokenExpired(token)

    private fun <T> extractClaim(token: String, claimsResolvers: (Claims) -> T): T =
        claimsResolvers(extractAllClaims(token))

    private fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        val accessToken: String = Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + this.securityProperties.tokenExpiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()

        val user = userRepository.findByEmail(userDetails.username)
            .orElseThrow {
                UsernameNotFoundException(I18n.HTTP_4XX_404_NOT_FOUND)
            }

        val accessTokenSaved: AccessToken = this.accessTokenRepository.save(
            AccessToken(
                token = accessToken,
                revoked = false,
                expired = false,
                expiredAt = LocalDateTime.now().plusSeconds(this.securityProperties.tokenExpiration),
                user = user
            )
        )

        this.refreshTokenRepository.save(
            RefreshToken(
                RefreshTokenKey(user, accessTokenSaved),
                revoked = false,
                token = generateRefreshToken(),
                expiresAt = LocalDateTime.now().plusSeconds(this.securityProperties.refreshTokenExpiration)
            )
        )

        return accessToken
    }

    private fun generateRefreshToken(): String {
        val secureRandom = SecureRandom()
        val randomBytes = ByteArray(24)
        secureRandom.nextBytes(randomBytes)
        val randomBytesEncoded: String = Base64.getUrlEncoder().encodeToString(randomBytes)

        return "${UUID.randomUUID()}.${System.currentTimeMillis()}.$randomBytesEncoded"
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    private fun extractAllClaims(token: String): Claims =
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).body

    private fun getSigningKey(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.securityProperties.jwtSigningKey))

    override fun loadUserByUsername(username: String): UserDetails {
        AccessManagerServiceImpl.LOGGER.info("[userDetailsService] Fetching user details for: $username")
        return userRepository.findByEmail(username)
            .orElseThrow {
                AccessManagerServiceImpl.LOGGER.error("[userDetailsService] User not found: $username")
                UsernameNotFoundException(I18n.HTTP_4XX_404_NOT_FOUND)
            }.also {
                AccessManagerServiceImpl.LOGGER.info(
                    "[userDetailsService] User details loaded successfully for: $username"
                )
            }
    }
}
