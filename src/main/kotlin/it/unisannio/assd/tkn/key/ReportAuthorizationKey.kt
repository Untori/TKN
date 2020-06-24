package it.unisannio.assd.tkn.key

import cafe.cryptography.ed25519.Ed25519PrivateKey
import cafe.cryptography.ed25519.Ed25519PublicKey
import it.unisannio.assd.tkn.Const
import it.unisannio.assd.tkn.toHexByteArray
import it.unisannio.assd.tkn.toHexString
import java.security.MessageDigest
import java.security.SecureRandom

class ReportAuthorizationKey private constructor(private val key: Ed25519PrivateKey) {

    fun deriveVerificationKey(): ReportVerificationKey =
        ReportVerificationKey.createFromAuthorizationKey(this)

    fun baseTemporaryContactKey(): TemporaryContactKey {
        val hmac = MessageDigest.getInstance("SHA-256").apply {
            update(Const.H_TCK_DOMAIN_SEPARATOR)
            update(key.toByteArray())
        }

        return TemporaryContactKey.createFromByteArray(
            hmac.digest(),
            0
        )
    }

    fun sign(message: ByteArray, rvk: ReportVerificationKey): ByteArray = key.expand()
        .sign(
            message,
            Ed25519PublicKey.fromByteArray(rvk.toByteArray())
        ).toByteArray()

    fun toByteArray(): ByteArray = key.toByteArray()

    fun toHexString() = toByteArray().toHexString()

    companion object {
        fun createFromByteArray(bytes: ByteArray): ReportAuthorizationKey =
            ReportAuthorizationKey(
                Ed25519PrivateKey.fromByteArray(bytes)
            )

        fun createFromHexString(hexString: String): ReportAuthorizationKey =
            ReportAuthorizationKey(
                Ed25519PrivateKey.fromByteArray(hexString.toHexByteArray())
            )

        fun createFromSecureRandom(random: SecureRandom): ReportAuthorizationKey =
            ReportAuthorizationKey(
                Ed25519PrivateKey.generate(random)
            )
    }
}
