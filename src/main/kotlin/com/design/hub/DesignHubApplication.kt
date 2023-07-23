package com.design.hub

import com.design.hub.configuration.SecurityProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties(SecurityProperties::class)
class DesignHubApplication

fun main(args: Array<String>) {
    runApplication<DesignHubApplication>(*args)
}
