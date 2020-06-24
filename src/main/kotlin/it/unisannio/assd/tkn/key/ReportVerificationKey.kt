package it.unisannio.assd.tkn.key

import cafe.cryptography.ed25519.Ed25519PrivateKey
import cafe.cryptography.ed25519.Ed25519PublicKey
import cafe.cryptography.ed25519.Ed25519Signature
import it.unisannio.assd.tkn.toHexString

class ReportVerificationKey private constructor(private val key: Ed25519PublicKey) {
    fun contactNumbersBetween(
        tck: TemporaryContactKey,
        from: Short,
        until: Short
    ) = tck.contactNumbersBetween(this, from, until)

    fun verify(message: ByteArray, sign: ByteArray): Boolean = key.verify(
        message,
        Ed25519Signature.fromByteArray(sign)
    )

    fun toByteArray(): ByteArray = key.toByteArray()

    fun toHexString() = toByteArray().toHexString()

    companion object {
        fun createFromAuthorizationKey(authorizationKey: ReportAuthorizationKey) =
            ReportVerificationKey(
                Ed25519PrivateKey.fromByteArray(authorizationKey.toByteArray())
                    .derivePublic()
            )

        fun createFromByteArray(bytes: ByteArray): ReportVerificationKey =
            ReportVerificationKey(
                Ed25519PublicKey.fromByteArray(bytes)
            )
    }
}
