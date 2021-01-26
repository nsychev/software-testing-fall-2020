package ru.nsychev.hashback.client

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.nsychev.hashback.security.TokenResponse

@Component
class AuthClient(
    @Value("\${auth.host}")
    private val host: String
) {
    fun getPublicKey(): String {
        val (_, _, result) =
            "${host}/api/auth/v1/public-key"
                .httpGet()
                .responseObject<TokenResponse>()

        return result.get().token
    }
}
