package com.design.hub.resource

import annotation.IntegrationTest
import com.design.hub.domain.user.User
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.UserCreateResponse
import com.design.hub.repository.UserRepository
import com.design.hub.service.AbstractCrudService
import com.design.hub.util.TestUserDataGenerator
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

@IntegrationTest
@DisplayName("User Resource Integration Tests")
@WithMockUser(
    value = "design_hub@email.com",
    authorities = ["ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_PROVIDER", "ROLE_BACKOFFICE"]
)
class UserResourceIntegrationTest {

    @Autowired
    private lateinit var flyway: Flyway

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun beforeEach() {
        flyway.clean()
        flyway.migrate()
    }

    @Test
    fun `logger should be initialized`() {
        assertNotNull(AbstractCrudService.LOGGER)
    }

    @Test
    fun `it should create an user`() {
        val triple: Triple<UserCreateRequest, UserCreateResponse, User> =
            TestUserDataGenerator.generateUserCreateRequestResponseUserTriple()

        val response = this.mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(triple.first)
            )
        ).andExpect(status().isCreated).andReturn()

        assertAll(
            { assertNotNull(triple.second.id) },
            { assert(response.response.contentAsString.contains(triple.second.name)) },
            { assert(response.response.contentAsString.contains(triple.second.email)) }
        )
    }

    @Test
    fun `it should retrieve an user by its ID`() {
        val userSaved = this.userRepository.save(TestUserDataGenerator.generateUser())

        val response = this.mockMvc.perform(
            get("/users/${userSaved.id}").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk).andReturn()

        assertAll(
            { assert(response.response.contentAsString.contains(userSaved.id.toString())) },
            { assert(response.response.contentAsString.contains(userSaved.name)) },
            { assert(response.response.contentAsString.contains(userSaved.email)) }
        )
    }

    @Test
    fun `it should return ResourceNotFound when user not found`() {
        val randomUUID = UUID.randomUUID()

        val response = this.mockMvc.perform(
            get("/users/$randomUUID")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)
            .andReturn()

        assertTrue(response.response.contentAsString.contains("Resource not found"))
    }

    @Test
    fun `it should retrieve an user page`() {
        val users: MutableList<User> = ArrayList()
        for (i in 1..(2..20).random()) {
            users.add(TestUserDataGenerator.generateUser())
        }

        this.userRepository.saveAll(users)

        val response = this.mockMvc.perform(
            get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.content.length()").value(users.size))
            .andReturn()

        val resultUsers: Page<UserCreateResponse> = objectMapper.readValue(
            response.response.contentAsString,
            object : TypeReference<Page<UserCreateResponse>>() {}
        )

        assertTrue(
            users.all { createdUser ->
                resultUsers.any { it.id == createdUser.id }
                resultUsers.any { it.name == createdUser.name }
                resultUsers.any { it.email == createdUser.email }
            }
        )
    }

    @Test
    fun `it should update an user`() {
        val userSaved = this.userRepository.save(TestUserDataGenerator.generateUser())
        val updateRequest = TestUserDataGenerator.generateUserCreateRequest()

        val response = this.mockMvc.perform(
            put("/users/${userSaved.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest))
        ).andExpect(status().isOk).andReturn()

        val resultUser: UserCreateResponse = objectMapper.readValue(
            response.response.contentAsString,
            object : TypeReference<UserCreateResponse>() {}
        )

        assertAll(
            { assertNotEquals(userSaved.name, resultUser.name) },
            { assertNotEquals(userSaved.email, resultUser.email) }
        )
    }

    @Test
    fun `it should return ResourceNotFound when it try update an user when that not found`() {
        val randomUUID = UUID.randomUUID()

        val response = this.mockMvc.perform(
            put("/users/$randomUUID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestUserDataGenerator.generateUserCreateRequest()))
        ).andExpect(status().isNotFound)
            .andReturn()

        assertTrue(response.response.contentAsString.contains("Resource not found"))
    }

    @Test
    fun `it should delete an user`() {
        val userSaved = this.userRepository.save(TestUserDataGenerator.generateUser())

        this.mockMvc.perform(
            delete("/users/${userSaved.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent)

        val deletedUser = this.userRepository.findById(userSaved.id)
        assertTrue(deletedUser.isEmpty)
    }

    @Test
    fun `it should return ResourceNotFound when it try delete an user when that not found`() {
        val randomUUID = UUID.randomUUID()

        val response = this.mockMvc.perform(
            delete("/users/$randomUUID")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)
            .andReturn()

        assertTrue(response.response.contentAsString.contains("Resource not found"))
    }
}
