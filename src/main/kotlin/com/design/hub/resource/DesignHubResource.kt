package com.design.hub.resource

import com.design.hub.domain.AbstractEntity
import com.design.hub.payload.PayloadRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import java.util.UUID

interface DesignHubResource {
    fun create(entity: PayloadRequest)

    fun findById(id: UUID): ResponseEntity<AbstractEntity>

    fun findAll(pageable: Pageable): ResponseEntity<Page<AbstractEntity>>

    fun update(id: UUID, entity: AbstractEntity): ResponseEntity<AbstractEntity>

    fun delete(id: UUID): ResponseEntity<Unit>
}
