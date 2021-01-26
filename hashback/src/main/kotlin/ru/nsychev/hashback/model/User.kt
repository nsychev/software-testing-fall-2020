package ru.nsychev.hashback.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.lang.IllegalArgumentException
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name="users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = -1,
    @Column(unique = true)
    @Size(min = 1, max = 120)
    private val username: String = "",
    @OneToMany(mappedBy="user")
    private val hashes: List<SavedHash> = listOf()
) : UserDetails {
    override fun getAuthorities() = listOf(GrantedAuthority { "USER" })

    override fun getPassword(): String = throw IllegalArgumentException("No password")

    override fun getUsername() = username

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}

