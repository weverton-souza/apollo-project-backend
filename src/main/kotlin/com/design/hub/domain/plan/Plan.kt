package com.design.hub.domain.plan

import com.design.hub.domain.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import java.util.UUID

@Entity
@Table(name = "\"plan\"")
@Where(clause = "deleted = false")
class Plan(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    override val id: UUID? = null,

    @Column(name = "name", nullable = false, length = 10)
    val name: String,

    @Column(name = "price", nullable = false)
    val price: Float
) : AbstractEntity()
