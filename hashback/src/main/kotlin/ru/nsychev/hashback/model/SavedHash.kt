package ru.nsychev.hashback.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.Size

@Entity
@Table(name="hashes")
data class SavedHash(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = -1,
    @Column(unique = true)
    @Size(min = 1, max = 256)
    val source: String = "",
    @Column(unique = true)
    @Size(min = 16, max = 128)
    val hash: String = "",
    @ManyToOne
    @JoinColumn(name="user_id")
    val user: User = User()
)
