package com.design.hub

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@DisplayName("Design Hub Application Tests")
class DesignHubApplicationTests {
    @Test
    fun contextLoads() {}
}
