package it.unisannio.assd.tkn.report

import it.unisannio.assd.tkn.TestConst
import it.unisannio.assd.tkn.key.ReportAuthorizationKey
import it.unisannio.assd.tkn.toHexByteArray
import it.unisannio.assd.tkn.toHexString
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class ReportTest {
    @Test
    fun readReportFromByteArray() {
        val report = Report.readReportFromByteArray(
            TestConst.REPORT_MESSAGE.toHexByteArray()
        )

        report.toHexString() `should be equal to` TestConst.REPORT_MESSAGE
    }

    @Test
    fun signWith() {
        val report = Report.readReportFromByteArray(
            TestConst.REPORT_MESSAGE.toHexByteArray()
        )
        val rak = ReportAuthorizationKey
            .createFromHexString(TestConst.PRIVATE_KEY_STRING_HEX)

        report.signWith(rak).sign
            .toHexString() `should be equal to` TestConst.REPORT_SIGN
    }

    @Test
    fun generateContactNumbers() {
        val report = Report.readReportFromByteArray(
            TestConst.REPORT_MESSAGE.toHexByteArray()
        )

        report.generateContactNumbers()
            .take(2).map { it.toHexString() } `should be equal to` listOf(
            TestConst.TCN_2,
            TestConst.TCN_3
        )

    }

    @Test
    fun toReportData() {
        val report = Report.readReportFromByteArray(
            TestConst.REPORT_MESSAGE.toHexByteArray()
        )

        val reportData = report.toReportData()

        reportData.rvk
            .toHexString() `should be equal to` TestConst.PUBLIC_KEY_STRING_HEX

        reportData.tck
            .toHexString() `should be equal to` TestConst.TCK_1

        reportData.from `should be equal to` TestConst.REPORT_FROM

        reportData.until `should be equal to` TestConst.REPORT_UNTIL

        reportData.memo `should be equal to` TestConst.REPORT_MEMO
    }

    @Test
    fun getVerificationKey() {
        val report = Report.readReportFromByteArray(
            TestConst.REPORT_MESSAGE.toHexByteArray()
        )

        report.getVerificationKey()
            .toHexString() `should be equal to` TestConst.PUBLIC_KEY_STRING_HEX
    }
}
