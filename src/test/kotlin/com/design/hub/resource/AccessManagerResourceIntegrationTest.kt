package com.design.hub.resource

import annotation.IntegrationTest
import com.design.hub.payload.security.request.RefreshTokenRequest
import com.design.hub.payload.security.request.SigninRequest
import com.design.hub.payload.security.response.JwtAuthenticationResponse
import com.design.hub.repository.UserRepository
import com.design.hub.resource.impl.AccessManagerResourceImpl
import com.design.hub.util.TestUserDataGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils
import java.lang.Thread.sleep

@IntegrationTest
@DisplayName("Access Manager Resource Integration Tests")
@WithMockUser(
    value = "design_hub@email.com",
    authorities = ["ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_PROVIDER", "ROLE_BACKOFFICE"]
)
class AccessManagerResourceIntegrationTest {

    @Autowired
    private lateinit var flyway: Flyway

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun beforeEach() {
        flyway.clean()
        flyway.migrate()
    }

    @Test
    fun `logger should be initialized`() {
        assertNotNull(AccessManagerResourceImpl.LOGGER)
    }

    @Test
    fun `it should sign up a user`() {
        val signupRequest = TestUserDataGenerator.generateUserCreateRequest()

        val response = this.mockMvc.perform(
            post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(signupRequest)
                )
        ).andExpect(status().isOk)
            .andReturn()

        val signupResponse = objectMapper
            .readValue(response.response.contentAsString, JwtAuthenticationResponse::class.java)

        assertNotNull(signupResponse)
        assertNotNull(signupResponse.accessToken)
        assertNotNull(signupResponse.expiration)
        assertNotNull(signupResponse.profile)
    }

    @Test
    fun `it should sign in a user`() {
        val password: String = RandomStringUtils.randomAlphanumeric(10)

        val user = TestUserDataGenerator.generateUser(password = passwordEncoder.encode(password))
        this.userRepository.save(user)

        val request = SigninRequest(email = user.email, password = password)

        val response = this.mockMvc.perform(
            post("/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andReturn()

        val signinResponse = objectMapper
            .readValue(response.response.contentAsString, JwtAuthenticationResponse::class.java)

        assertNotNull(signinResponse)
        assertNotNull(signinResponse.accessToken)
        assertNotNull(signinResponse.expiration)
        assertNotNull(signinResponse.profile)
    }

    @Test
    fun `it should refresh a token`() {
        val password: String = RandomStringUtils.randomAlphanumeric(10)

        val user = TestUserDataGenerator.generateUser(password = passwordEncoder.encode(password))
        this.userRepository.save(user)

        val request = SigninRequest(email = user.email, password = password)

        val response = this.mockMvc.perform(
            post("/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andReturn()

        val signinResponse = objectMapper
            .readValue(response.response.contentAsString, JwtAuthenticationResponse::class.java)

        sleep(1000)
        val refreshTokenRequest = RefreshTokenRequest(refreshToken = signinResponse.refreshToken)

        val response2 = this.mockMvc.perform(
            post("/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshTokenRequest))
        )
            .andExpect(status().isOk)
            .andReturn()

        val refreshTokenResponse = objectMapper
            .readValue(response2.response.contentAsString, JwtAuthenticationResponse::class.java)

        assertNotNull(refreshTokenResponse)
        assertNotNull(refreshTokenResponse.accessToken)
        assertNotNull(refreshTokenResponse.expiration)
        assertNotNull(refreshTokenResponse.profile)
    }
}
