package com.design.hub.domain.plan

import com.design.hub.domain.AbstractEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import java.util.UUID

@Entity
@Table(name = "\"product\"")
@Where(clause = "deleted = false")
class Product(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    override var id: UUID = UUID.randomUUID(),

    @Column(name = "name", updatable = false)
    val name: String,

    @Column(name = "description", updatable = false)
    val description: String,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "product")
    val details: MutableList<ProductDetail> = mutableListOf()

) : AbstractEntity()
