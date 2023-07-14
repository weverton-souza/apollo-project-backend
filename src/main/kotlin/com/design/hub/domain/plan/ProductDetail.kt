package com.design.hub.domain.plan

import com.design.hub.domain.AbstractEntity
import com.design.hub.enumeration.CurrencyType
import com.design.hub.enumeration.PeriodType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID
import org.hibernate.annotations.Where

@Entity
@Table(name = "\"product_detail\"")
@Where(clause = "deleted = false")
class ProductDetail(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    override val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @Column(name = "price", nullable = false)
    val price: Float,

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    val currency: CurrencyType,

    @Enumerated(EnumType.STRING)
    @Column(name = "period", nullable = false)
    val period: PeriodType

) : AbstractEntity()
