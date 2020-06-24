package it.unisannio.assd.tkn.key

import cafe.cryptography.ed25519.Ed25519PrivateKey
import it.unisannio.assd.tkn.TestConst.MESSAGE
import it.unisannio.assd.tkn.TestConst.PRIVATE_KEY_STRING_HEX
import it.unisannio.assd.tkn.TestConst.PUBLIC_KEY_STRING_HEX
import it.unisannio.assd.tkn.TestConst.TCK_0
import it.unisannio.assd.tkn.toHexByteArray
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class ReportAuthorizationKeyTest {
    @Test
    fun createFromByteArray() {
        val keyBytes = PRIVATE_KEY_STRING_HEX.toHexByteArray()

        val rak = ReportAuthorizationKey.createFromByteArray(keyBytes)

        rak.toHexString() `should be equal to` PRIVATE_KEY_STRING_HEX
    }

    @Test
    fun createFromHexString() {
        val rak = ReportAuthorizationKey.createFromHexString(PRIVATE_KEY_STRING_HEX)

        rak.toHexString() `should be equal to` PRIVATE_KEY_STRING_HEX
    }

    @Test
    fun deriveVerificationKey() {
        val rak = ReportAuthorizationKey.createFromHexString(PRIVATE_KEY_STRING_HEX)

        rak.deriveVerificationKey()
            .toHexString() `should be equal to` PUBLIC_KEY_STRING_HEX
    }

    @Test
    fun deriveTck0() {
        val rak = ReportAuthorizationKey.createFromHexString(PRIVATE_KEY_STRING_HEX)

        rak.baseTemporaryContactKey()
            .toHexString() `should be equal to` TCK_0
    }

    @Test
    fun sign() {
        val rak = ReportAuthorizationKey.createFromHexString(PRIVATE_KEY_STRING_HEX)
        val rvk = rak.deriveVerificationKey()
        val privateKey = Ed25519PrivateKey.fromByteArray(PRIVATE_KEY_STRING_HEX.toHexByteArray())
        val publicKey = privateKey.derivePublic()

        val message = MESSAGE.toByteArray()

        rak.sign(message, rvk) `should be equal to` privateKey.expand().sign(message, publicKey).toByteArray()
    }
}
