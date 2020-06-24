package it.unisannio.assd.tkn.report

import it.unisannio.assd.tkn.TestConst
import it.unisannio.assd.tkn.toHexByteArray
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class SignedReportTest {
    @Test
    fun readFromByteArray() {
        val report = TestConst.REPORT_MESSAGE + TestConst.REPORT_SIGN

        SignedReport.readFromByteArray(
            report.toHexByteArray()
        ).toHexString() `should be equal to` report
    }

    @Test
    fun verify() {
        val report = TestConst.REPORT_MESSAGE + TestConst.REPORT_SIGN

        SignedReport.readFromByteArray(
            report.toHexByteArray()
        ).verify() `should be equal to` true
    }
}
