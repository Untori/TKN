package it.unisannio.assd.tkn.report

import it.unisannio.assd.tkn.key.ReportVerificationKey
import it.unisannio.assd.tkn.key.TemporaryContactKey

data class ReportData(
    val rvk: ReportVerificationKey,
    val tck: TemporaryContactKey,
    val from: Short,
    val until: Short,
    val memo: Memo
)
