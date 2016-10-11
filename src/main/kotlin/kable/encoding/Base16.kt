package kable.encoding

import java.math.BigInteger

fun Int.encodeToBase16() = if (this < 10) '0' + this else 'a' + (this - 10)

fun ByteArray.encodeToBase16(): String {

    val result = CharArray(size * 2)

    for (i in (0..(size - 1))) {
        result[i * 2] = (this[i].toInt() shr 4 and 0x0F).encodeToBase16()
        result[i * 2 + 1] = (this[i].toInt() and 0x0F).encodeToBase16()
    }

    return String(result)
}

fun BigInteger.encodeToBase16() = toByteArray().encodeToBase16()

fun Char.decodeFromBase16() = if (this >= '0' && this <= '9') this - '0' else this - 'a' + 10

fun CharArray.decodeFromBase16() =
        ByteArray((size + 1) / 2) { i ->
            ((this[i * 2].decodeFromBase16() shl 4) or this[i * 2 + 1].decodeFromBase16()).toByte()
        }


fun String.decodeFromBase16() = toCharArray().decodeFromBase16()
