package kable.encoding

/**
 * Hexadecimal base
 *
 * Contains the symbols : [0-9a-f]
 * @see Base
 */
object Hexadecimal : Base {

    override val symbolCount = 16

    override fun get(value: Byte): Char = when {
        value >= 0 && value < 10 -> '0' + value.toInt()
        value >= 10 && value < 16 -> 'a' + value.toInt() - 10
        else -> throw IllegalArgumentException("$value cannot be converted in an hexadecimal char")
    }

    override fun get(char: Char): Byte = when {
        char >= '0' && char <= '9' -> char - '0'
        char >= 'a' && char <= 'f' -> char - 'a' + 10
        char >= 'A' && char <= 'F' -> char - 'A' + 10
        else -> throw IllegalArgumentException("'$char' is not an hexadecimal char")
    }.toByte()

    override fun contains(char: Char): Boolean =
            (char >= '0' && char <= '9') || (char >= 'a' && char <= 'f') || (char >= 'A' && char <= 'F')
}

/**
 * Return the hexadecimal representation of the byte array
 */
fun ByteArray.encodeToHexadecimal(): String {

    val result = CharArray(size * 2)

    for (i in (0..(size - 1))) {
        result[i * 2] = (this[i].toInt() shr 4 and 0x0F).toByte().let { Hexadecimal[it] }
        result[i * 2 + 1] = (this[i].toInt() and 0x0F).toByte().let { Hexadecimal[it] }
    }

    return String(result)
}

/**
 * Return the the byte array represented by the char array in hexadecimal
 */
fun CharArray.decodeFromHexadecimal() =
        ByteArray((size + 1) / 2) { i ->
            ((Hexadecimal[this[i * 2]].toInt() shl 4).toByte() + Hexadecimal[this[i * 2 + 1]]).toByte()
        }

/**
 * Return the the byte array represented by the string in hexadecimal
 */
fun String.decodeFromHexadecimal() = toCharArray().decodeFromHexadecimal()