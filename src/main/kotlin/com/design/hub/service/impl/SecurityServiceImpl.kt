package com.design.hub.service.impl

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
import java.util.Date

@Service
class SecurityServiceImpl(
    private val userRepository: UserRepository
) : SecurityService, UserDetailsService {
    private val jwtSigningKey: String = "5Kg6WrL1NsY2ToH4QjXzBa9Dc3Fv7Mu8IpORmSeVtZnCp0yJlUi"

    override fun extractUserName(token: String): String = extractClaim<String>(token, Claims::getSubject)

    override fun generateToken(userDetails: UserDetails): String = generateToken(HashMap(), userDetails)

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val userName = extractUserName(token)
        return userName == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, claimsResolvers: (Claims) -> T): T =
        claimsResolvers(extractAllClaims(token))

    private fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact()
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    private fun extractAllClaims(token: String): Claims =
        Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).body

    private fun getSigningKey(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey))

    override fun loadUserByUsername(username: String): UserDetails {
        AccessManagerServiceImpl.LOGGER.info("[userDetailsService] Fetching user details for: $username")
        return userRepository.findByEmail(username)
            .orElseThrow {
                AccessManagerServiceImpl.LOGGER.error("[userDetailsService] User not found: $username")
                UsernameNotFoundException(I18n.HTTP_4XX_404_NOT_FOUND)
            }.also {
                AccessManagerServiceImpl.LOGGER.info("[userDetailsService] User details loaded successfully for: $username")
            }
    }
}
