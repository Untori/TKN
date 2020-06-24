package it.unisannio.assd.tkn

object Const {
    private const val H_TCK_DOMAIN_SEPARATOR_STRING = "H_TCK"
    val H_TCK_DOMAIN_SEPARATOR = H_TCK_DOMAIN_SEPARATOR_STRING
        .toByteArray(Charsets.UTF_8)

    private const val H_TCN_DOMAIN_SEPARATOR_STRING = "H_TCN"
    val H_TCN_DOMAIN_SEPARATOR = H_TCN_DOMAIN_SEPARATOR_STRING
        .toByteArray(Charsets.UTF_8)

    const val REPORT_SIZE = 70
}
