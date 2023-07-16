package com.design.hub.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional
import java.util.UUID

interface DesignHubService<T> {
    fun create(entity: T): T

    fun findById(id: UUID): Optional<T>

    fun findAll(pageable: Pageable): Page<T>

    fun update(id: UUID, entity: T): T

    fun delete(id: UUID): Boolean
}
