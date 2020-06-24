package it.unisannio.assd.tkn.key

import it.unisannio.assd.tkn.TestConst
import it.unisannio.assd.tkn.toHexByteArray
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class TemporaryContactKeyTest {
    @Test
    fun createFromByteArray() {
        val tck0 = TemporaryContactKey
            .createFromByteArray(TestConst.TCK_0.toHexByteArray(), 0)

        tck0.toHexString() `should be equal to` TestConst.TCK_0
    }

    @Test
    fun createFromHexString() {
        val tck0 = TemporaryContactKey
            .createFromHexString(TestConst.TCK_0, 0)

        tck0.toHexString() `should be equal to` TestConst.TCK_0
    }

    @Test
    fun nextTemporaryKey() {
        val tck0 = TemporaryContactKey
            .createFromHexString(TestConst.TCK_0, 0)
        val rvk = ReportVerificationKey
            .createFromHexString(TestConst.PUBLIC_KEY_STRING_HEX)

        tck0.nextTemporaryContactKey(rvk)
            .toHexString() `should be equal to` TestConst.TCK_1
    }

    @Test
    fun derivateTemporaryContactNumber() {
        val tck1 = TemporaryContactKey
            .createFromHexString(TestConst.TCK_1, 1)

        tck1.deriveTemporaryContactNumber()
            .toHexString() `should be equal to` TestConst.TCN_1
    }

    @Test
    fun generateReport() {
        val tck1 = TemporaryContactKey
            .createFromHexString(TestConst.TCK_1, 1)
        val rvk = ReportVerificationKey
            .createFromHexString(TestConst.PUBLIC_KEY_STRING_HEX)

        tck1.generateReport(rvk, 10, "symptom data")
            .toHexString() `should be equal to` TestConst.REPORT_MESSAGE
    }
}
