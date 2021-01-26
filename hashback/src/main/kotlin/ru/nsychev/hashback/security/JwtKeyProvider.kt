package ru.nsychev.hashback.security

import com.auth0.jwt.interfaces.ECDSAKeyProvider
import java.security.interfaces.ECPublicKey
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.nsychev.hashback.service.IdentityService

@Component
class JwtKeyProvider : ECDSAKeyProvider {
    private var publicKey: ECPublicKey? = null

    @Autowired
    private lateinit var identityService: IdentityService

    private fun loadPublicKey(): ECPublicKey {
        val publicKey = identityService.loadPublicKey()
        this.publicKey = publicKey
        return publicKey
    }

    override fun getPublicKeyById(keyId: String): ECPublicKey {
        return publicKey ?: loadPublicKey()
    }

    override fun getPrivateKey() = TODO("Not yet implemented")
    override fun getPrivateKeyId() = TODO("Not yet implemented")
}
