package ru.nsychev.hashback.service

import org.bouncycastle.util.io.pem.PemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.nsychev.hashback.client.AuthClient
import java.io.StringReader
import java.security.KeyFactory
import java.security.interfaces.ECPublicKey
import java.security.spec.X509EncodedKeySpec

@Service
class IdentityService {
    @Autowired
    private lateinit var authClient: AuthClient

    fun loadPublicKey(): ECPublicKey {
        val pem = authClient.getPublicKey()
        return parsePublicKey(parsePem(pem))
    }

    private fun parsePem(content: String): ByteArray {
        return PemReader(StringReader(content)).use { reader ->
            reader.readPemObject().content
        }
    }

    private fun parsePublicKey(content: ByteArray): ECPublicKey {
        val keyFactory = KeyFactory.getInstance("EC")
        val keySpec = X509EncodedKeySpec(content)
        return keyFactory.generatePublic(keySpec) as ECPublicKey
    }
}
