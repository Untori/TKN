package it.unisannio.assd.tkn.key

import it.unisannio.assd.tkn.Const
import it.unisannio.assd.tkn.report.Memo
import it.unisannio.assd.tkn.report.Report
import it.unisannio.assd.tkn.toHexString
import it.unisannio.assd.tkn.toLeByteArray
import java.security.MessageDigest

class TemporaryContactKey private constructor(
    private val key: ByteArray,
    private val index: Short
) {
    fun nextTemporaryContactKey(rvk: ReportVerificationKey): TemporaryContactKey {
        val hmac = MessageDigest.getInstance("SHA-256").apply {
            update(Const.H_TCK_DOMAIN_SEPARATOR)
            update(rvk.toByteArray())
            update(key)
        }

        return TemporaryContactKey(
            hmac.digest(),
            index.inc()
        )
    }

    fun deriveTemporaryContactNumber(): TemporaryContactNumber {
        val hmac = MessageDigest.getInstance("SHA-256").apply {
            update(Const.H_TCN_DOMAIN_SEPARATOR)
            update(index.toLeByteArray())
            update(key)
        }

        return TemporaryContactNumber(
            hmac.digest().sliceArray(0 until 16),
            index
        )
    }

    fun contactNumbersBetween(
        rvk: ReportVerificationKey,
        from: Short,
        until: Short
    ): List<TemporaryContactNumber> {
        val numbers = mutableListOf<TemporaryContactNumber>()
        var lastKey = this

        for (i in from until until) {
            lastKey = lastKey.nextTemporaryContactKey(rvk)
            numbers.add(lastKey.deriveTemporaryContactNumber())
        }

        return numbers
    }

    fun generateReport(rvk: ReportVerificationKey, until: Short, memo: Memo): Report = Report.createReport(
        rvk,
        this,
        index.inc(),
        until,
        memo
    )

    fun toByteArray() = key

    fun toHexString() = key.toHexString()

    companion object {
        fun createFromByteArray(bytes: ByteArray, index: Short): TemporaryContactKey =
            TemporaryContactKey(
                bytes,
                index
            )
    }
}
