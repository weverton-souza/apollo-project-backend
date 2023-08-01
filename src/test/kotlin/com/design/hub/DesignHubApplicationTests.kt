package com.design.hub

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class DesignHubApplicationTests {

    @Autowired
    private lateinit var flyway: Flyway

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DesignHubApplicationTests::class.java)
        private val container = SingletonPostgresContainer.instance

        @JvmStatic
        @DynamicPropertySource
        fun registerPgProperties(registry: DynamicPropertyRegistry) {
            LOGGER.info("[registerPgProperties] Configuring database properties")

            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.password", container::getPassword)

            LOGGER.info("[registerPgProperties] Configured database properties")
            LOGGER.info("[registerPgProperties] spring.datasource.url: ${container.jdbcUrl}")
            LOGGER.info("[registerPgProperties] spring.datasource.username: ${container.username}")
            LOGGER.info("[registerPgProperties] spring.datasource.password: ${container.password}")
        }
    }

    @Test
    fun contextLoads() {
        LOGGER.info("[contextLoads] Running tests...")
    }

    @BeforeEach
    fun beforeEach() {
        LOGGER.info("[beforeEach] Cleaning and migrating database")
        flyway.clean()
        flyway.migrate()
    }
}
