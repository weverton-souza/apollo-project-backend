package com.design.hub.service

import com.design.hub.domain.AbstractEntity
import com.design.hub.exception.ResourceNotFoundException
import com.design.hub.utils.I18n
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

abstract class AbstractCrudService<T : AbstractEntity>(
    protected val repository: JpaRepository<T, UUID>
) : DesignHubService<T> {

    companion object {
        private val logger = LoggerFactory.getLogger(AbstractCrudService::class.java)
    }

    override fun create(entity: T): T {
        logger.info("[create] Creating entity")
        val createdEntity = this.repository.save(entity)
        logger.info("[create] Entity created successfully: ${createdEntity.id}")
        return createdEntity
    }

    override fun findById(id: UUID): Optional<T> {
        logger.info("[findById] Fetching entity by ID: {}", id)
        return this.repository.findById(id)
    }

    override fun findAll(pageable: Pageable): Page<T> {
        logger.info("[findAll] Fetching all entities")
        val entities = this.repository.findAll(pageable)
        logger.info("[findAll] Total entities found: ${entities.size}")
        return entities
    }

    override fun update(id: UUID, entity: T): T {
        logger.info("[update] Updating entity with ID $id")
        return if (this.repository.existsById(id)) {
            logger.info("[update] Entity updated successfully: $id")
            this.repository.save(entity)
        } else {
            logger.info("[update] Entity not found for ID: $id")
            throw ResourceNotFoundException(I18n.HTTP_4XX_404_NOT_FOUND)
        }
    }

    override fun delete(id: UUID): Boolean {
        logger.info("[delete] Deleting entity with ID: $id")
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id)
            logger.info("[delete] Entity deleted successfully with ID: $id")
            return true
        }
        logger.info("[delete] Entity not found for ID: $id")
        return false
    }
}
