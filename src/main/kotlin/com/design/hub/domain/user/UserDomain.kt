package com.design.hub.domain.user

import com.design.hub.domain.AbstractEntity
import com.design.hub.domain.security.RoleDomain
import com.design.hub.enumeration.AccountStatus
import com.design.hub.enumeration.UserType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "\"user\"")
@Where(clause = "deleted = false")
data class UserDomain(
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    override val id: UUID? = null,

    @Column(name = "name", nullable = false, length = 100)
    val name: String,

    @Column(name = "email", nullable = false, unique = true, length = 50)
    val email: String,

    @Column(name = "password", nullable = false, length = 64)
    private val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: UserType,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: AccountStatus,

    @Column(name = "verified", nullable = false)
    val verified: Boolean = false

) : AbstractEntity(), UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(type.name))

    override fun getPassword(): String = this.password

    override fun getUsername(): String = this.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
