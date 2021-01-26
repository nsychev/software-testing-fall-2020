package ru.nsychev.hashback.repo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import ru.nsychev.hashback.model.User

@Component
interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
}
