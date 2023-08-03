package com.design.hub.util

import com.design.hub.DesignHubApplicationTests
import com.design.hub.SingletonPostgresContainer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import java.util.concurrent.atomic.AtomicBoolean

@SpringBootTest
@DisplayName("Test Container Configuration Tests")
class TestContainerConfiguration : BeforeAllCallback {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DesignHubApplicationTests::class.java)

        private val atomicBoolean: AtomicBoolean = AtomicBoolean(false)
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

    override fun beforeAll(context: ExtensionContext) {
        if (atomicBoolean.compareAndSet(false, true)) {
            LOGGER.info("[beforeAll] Starting database container")
            container.start()
            LOGGER.info("[beforeAll] Started database container")
        }
    }
}
