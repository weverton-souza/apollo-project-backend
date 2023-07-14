package com.design.hub.domain.security

import com.design.hub.domain.AbstractEntity
import com.design.hub.enumeration.UserType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import java.util.UUID

@Entity
@Table(name = "\"role\"")
@Where(clause = "deleted = false")
class RoleDomain(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    override val id: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: UserType,

    @Column(name = "description", nullable = false)
    val description: String
) : AbstractEntity()
