package it.unisannio.assd.tkn.key

import it.unisannio.assd.tkn.toHexString
import java.nio.ByteBuffer
import java.util.UUID

class TemporaryContactNumber(
    private val number: ByteArray,
    private val index: Short
) {
    fun toHexString() = number.toHexString()

    fun toUUID(): UUID {
        val byteBuffer = ByteBuffer.wrap(number)
        val high = byteBuffer.long
        val low = byteBuffer.long
        return UUID(high, low)
    }

    fun toByteArray() = number
}
