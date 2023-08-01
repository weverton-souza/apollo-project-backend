package com.design.hub

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.PostgreSQLContainer

class SingletonPostgresContainer private constructor() {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(SingletonPostgresContainer::class.java)

        val instance: PostgreSQLContainer<*> by lazy {
            LOGGER.info("[SingletonPostgresContainer] Starting the PostgreSQL container")
            PostgreSQLContainer<Nothing>("postgres:13.3")
                .apply {
                    withDatabaseName("design-hub-database")
                    withUsername("design-hub-user")
                    withPassword("PIh4Yrv75BLP1SuXY9XU")
                }.also { it.start() }
        }
    }
}
