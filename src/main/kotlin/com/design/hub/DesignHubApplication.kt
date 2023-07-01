package com.design.hub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class DesignHubApplication

fun main(args: Array<String>) {
    runApplication<DesignHubApplication>(*args)
}
