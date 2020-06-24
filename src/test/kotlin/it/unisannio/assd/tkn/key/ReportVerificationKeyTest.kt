package it.unisannio.assd.tkn.key

import it.unisannio.assd.tkn.TestConst
import it.unisannio.assd.tkn.toHexByteArray
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class ReportVerificationKeyTest {
    @Test
    fun createFromByteArray() {
        val rvk = ReportVerificationKey
            .createFromByteArray(TestConst.PUBLIC_KEY_STRING_HEX.toHexByteArray())

        println(rvk.toHexString())

        rvk.toHexString() `should be equal to` TestConst.PUBLIC_KEY_STRING_HEX
    }

    @Test
    fun createFromAuthorizationKey() {
        val rak = ReportAuthorizationKey
            .createFromHexString(TestConst.PRIVATE_KEY_STRING_HEX)

        ReportVerificationKey
            .createFromAuthorizationKey(rak)
            .toHexString() `should be equal to` TestConst.PUBLIC_KEY_STRING_HEX
    }

    @Test
    fun createFromHexString() {
        val rvk = ReportVerificationKey
            .createFromHexString(TestConst.PUBLIC_KEY_STRING_HEX)

        rvk.toHexString() `should be equal to` TestConst.PUBLIC_KEY_STRING_HEX
    }

    @Test
    fun verify() {
        val rvk = ReportVerificationKey
            .createFromHexString(TestConst.PUBLIC_KEY_STRING_HEX)

        rvk.verify(
            TestConst.REPORT_MESSAGE.toHexByteArray(),
            TestConst.REPORT_SIGN.toHexByteArray()
        ) `should be equal to` true
    }

    @Test
    fun verifyFalse() {
        val rvk = ReportVerificationKey
            .createFromHexString(TestConst.PUBLIC_KEY_STRING_HEX)

        rvk.verify(
            "not original message".toByteArray(),
            TestConst.REPORT_SIGN.toHexByteArray()
        ) `should be equal to` false
    }

    @Test
    fun contactNumbersBetween() {
        val rak = ReportAuthorizationKey
            .createFromHexString(TestConst.PRIVATE_KEY_STRING_HEX)
        val rvk = ReportVerificationKey
            .createFromHexString(TestConst.PUBLIC_KEY_STRING_HEX)

        rvk.contactNumbersBetween(
            rak.baseTemporaryContactKey(),
            1,
            4
        ).map {
            it.toHexString()
        } `should be equal to` listOf(TestConst.TCN_1, TestConst.TCN_2, TestConst.TCN_3)
    }
}
