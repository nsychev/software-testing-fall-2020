package ru.nsychev.hashback.service

import org.springframework.stereotype.Service

@Service
class AuthService {
    fun checkToken(token: String): Boolean {
        return false
    }

}
