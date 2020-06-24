package it.unisannio.assd.tkn.report

import it.unisannio.assd.tkn.Const
import it.unisannio.assd.tkn.key.ReportAuthorizationKey
import it.unisannio.assd.tkn.key.ReportVerificationKey
import it.unisannio.assd.tkn.key.TemporaryContactKey
import it.unisannio.assd.tkn.read
import it.unisannio.assd.tkn.toHexString
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Report private constructor(private val report: ByteArray) {
    private val reportData by lazy {
        readReportDataFromByteArray(report)
    }

    fun signWith(rak: ReportAuthorizationKey): SignedReport =
        SignedReport.createFromReport(
            this,
            rak
        )

    fun getVerificationKey(): ReportVerificationKey = reportData.rvk

    fun toReportData(): ReportData = reportData

    fun toByteArray() = report

    fun toHexString() = report.toHexString()

    companion object {
        fun createReport(
            rvk: ReportVerificationKey,
            tck: TemporaryContactKey,
            from: Short,
            until: Short,
            memo: Memo
        ): Report {
            val memoData = memo.toByteArray()
            val buffer = ByteBuffer.allocate(Const.REPORT_SIZE + memoData.size).apply {
                order(ByteOrder.LITTLE_ENDIAN)
                put(rvk.toByteArray())
                put(tck.toByteArray())
                putShort(from)
                putShort(until)
                put(0) // TODO
                put(memoData.size.toByte())
                put(memoData)
            }

            return Report(buffer.array())
        }

        fun createReport(reportData: ReportData): Report =
            createReport(
                reportData.rvk,
                reportData.tck,
                reportData.from,
                reportData.until,
                reportData.memo
            )

        fun readReportFromByteArray(bytes: ByteArray): Report =
            createReport(
                readReportDataFromByteArray(bytes)
            )

        fun readReportDataFromByteArray(bytes: ByteArray): ReportData {
            val buffer = ByteBuffer.wrap(bytes).apply {
                order(ByteOrder.LITTLE_ENDIAN)
            }

            val rvk = ReportVerificationKey.createFromByteArray(buffer.read(32))
            val tckBytes = buffer.read(32)
            val from = buffer.short
            val until = buffer.short
            val memoType = buffer.get()
            val memoData = String(buffer.read(buffer.get().toInt()))

            return ReportData(
                rvk,
                TemporaryContactKey.createFromByteArray(tckBytes, from.dec()),
                from,
                until,
                memoData
            )
        }
    }
}
