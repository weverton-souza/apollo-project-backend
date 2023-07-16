package com.design.hub.domain.plan

import com.design.hub.domain.AbstractEntity
import com.design.hub.domain.user.UserDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "\"subscription\"")
@Where(clause = "deleted = false")
class Subscription(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    override val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserDomain,

    @OneToOne
    @JoinColumn(name = "plan_id")
    val plan: PlanDomain,

    @Column(name = "start_at", nullable = false)
    val startAt: LocalDateTime,

    @Column(name = "end_at")
    val endAt: LocalDateTime
) : AbstractEntity()
