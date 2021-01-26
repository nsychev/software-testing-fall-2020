package ru.nsychev.hashback.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.nsychev.hashback.model.User
import ru.nsychev.hashback.repo.UserRepository

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null || username == "") {
            throw UsernameNotFoundException("Pass a username")
        }

        return userRepository.findByUsername(username)
            ?: userRepository.save(User(-1, username))
    }
}
