package com.design.hub.util

import com.design.hub.domain.user.User
import com.design.hub.enumeration.AccountStatus
import com.design.hub.enumeration.UserType
import com.design.hub.payload.user.request.UserCreateRequest
import com.design.hub.payload.user.response.UserCreateResponse
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils
import java.util.UUID
import kotlin.random.Random

object TestUserDataGenerator {

    fun generateUserCreateRequest(): UserCreateRequest {
        val name = RandomStringUtils.randomAlphabetic(10)
        val email = "${RandomStringUtils.randomAlphabetic(5)}@example.com"
        val password = RandomStringUtils.randomAlphanumeric(10)
        val userType = if (Random.nextBoolean()) "BACKOFFICE" else "CUSTOMER"

        return UserCreateRequest(name, email, password, userType)
    }

    fun generateUser(password: String = RandomStringUtils.randomAlphanumeric(10)): User {
        val id = UUID.randomUUID()
        val name = RandomStringUtils.randomAlphabetic(10)
        val email = "${RandomStringUtils.randomAlphabetic(5)}@example.com"
        val type = if (Random.nextBoolean()) UserType.BACKOFFICE else UserType.CUSTOMER
        val status = if (Random.nextBoolean()) AccountStatus.PENDING_APPROVAL else AccountStatus.SUSPENDED
        val verified = Random.nextBoolean()

        return User(id, name, email, password, type, status, verified)
    }

    fun generateUserCreateRequestResponseUserTriple(): Triple<UserCreateRequest, UserCreateResponse, User> {
        val id = UUID.randomUUID()
        val name = RandomStringUtils.randomAlphabetic(10)
        val email = "${RandomStringUtils.randomAlphabetic(5)}@example.com"
        val password = RandomStringUtils.randomAlphanumeric(10)
        val userTypeString = if (Random.nextBoolean()) "BACKOFFICE" else "CUSTOMER"
        val userType = UserType.valueOf(userTypeString)

        val request = UserCreateRequest(name, email, password, userTypeString)

        val response = UserCreateResponse(id, name, email, userType, false, AccountStatus.PENDING_APPROVAL)

        val user = User(
            id = id,
            name = name,
            email = email,
            password = password,
            type = userType,
            status = AccountStatus.PENDING_APPROVAL,
            verified = false
        )

        return Triple(request, response, user)
    }
}
