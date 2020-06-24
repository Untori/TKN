package it.unisannio.assd.tkn

import java.nio.ByteBuffer
import java.nio.ByteOrder

fun Short.toLeByteArray(): ByteArray {
    val buffer = ByteBuffer
        .allocate(2).apply {
            order(ByteOrder.LITTLE_ENDIAN)
            putShort(this@toLeByteArray)
        }

    return buffer.array()
}

fun ByteArray.toHexString(): String {
    val builder = StringBuilder()
    this.map { String.format("%02x", it) }
        .forEach { builder.append(it) }
    return builder.toString()
}

fun String.toHexByteArray(): ByteArray {
    val b = ByteArray(this.length / 2)
    for (i in b.indices) {
        val index = i * 2
        val v: Int = this.substring(index, index + 2).toInt(16)
        b[i] = v.toByte()
    }
    return b
}

fun ByteBuffer.read(n: Int): ByteArray {
    val bytes = ByteArray(n)
    this.get(bytes)
    return bytes
}
