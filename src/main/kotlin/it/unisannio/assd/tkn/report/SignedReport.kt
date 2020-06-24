package it.unisannio.assd.tkn.report

import it.unisannio.assd.tkn.key.ReportAuthorizationKey
import it.unisannio.assd.tkn.read
import it.unisannio.assd.tkn.toHexString
import java.nio.ByteBuffer
import java.nio.ByteOrder

class SignedReport private constructor(
    val report: Report,
    private val sign: ByteArray
) {
    fun verify(): Boolean = report.getVerificationKey()
        .verify(report.toByteArray(), sign)

    fun toByteArray(): ByteArray {
        val reportBytes = report.toByteArray()
        val buffer = ByteBuffer.allocate(reportBytes.size + sign.size).apply {
            put(reportBytes)
            put(sign)
        }

        return buffer.array()
    }

    fun toHexString(): String = toByteArray().toHexString()

    companion object {
        fun createFromReport(
            report: Report,
            rak: ReportAuthorizationKey
        ): SignedReport =
            SignedReport(
                report,
                rak.sign(report.toByteArray(), report.getVerificationKey())
            )

        fun readFromByteArray(bytes: ByteArray): SignedReport {
            val report = Report.readReportFromByteArray(bytes)

            val buffer = ByteBuffer.wrap(bytes).apply {
                order(ByteOrder.LITTLE_ENDIAN)
                read(report.toByteArray().size)
            }

            val sign = buffer.read(buffer.remaining())

            return SignedReport(report, sign)
        }
    }
}
