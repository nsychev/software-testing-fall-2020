package ru.nsychev.hashback.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException
import ru.nsychev.hashback.model.SavedHash
import ru.nsychev.hashback.model.User
import ru.nsychev.hashback.service.SavedHashService
import java.math.BigInteger
import java.security.MessageDigest
import java.security.Principal

@RestController
class SavedHashController {
    @Autowired
    private lateinit var savedHashService: SavedHashService

    @GetMapping("/hash/{hash}")
    fun getHash(@PathVariable hash: String): SavedHash {
        return savedHashService.retrieve(hash)
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/hash/")
    fun addHash(@RequestBody text: String, authentication: Authentication): String {
        val hashValue = MessageDigest.getInstance("MD5").let { md ->
            BigInteger(1, md.digest(text.toByteArray())).toString(16).padStart(32, '0')
        }
        val hash = savedHashService.retrieve(hashValue)
        if (hash != null) {
            throw HttpClientErrorException(HttpStatus.CONFLICT)
        }

        savedHashService.save(SavedHash(
            -1,
            text,
            hashValue,
            authentication.principal as User
        ))

        return hashValue
    }
}
