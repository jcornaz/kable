package kable.encoding

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class Base16Test {

    val rng = Random()

    fun Random.nextByteArray(size: Int) = ByteArray(size).apply { this@nextByteArray.nextBytes(this) }

    @Test
    fun testEncodedFormat() {
        (1..1000).forEach {
            assertTrue(Regex("^[0-9a-f]{32}$").matches(rng.nextByteArray(16).encodeToBase16()))
        }
    }

    @Test
    fun testEncodeDecode() {
        (1..1000).forEach {
            val decoded: ByteArray = rng.nextByteArray(16)
            val encoded: String = decoded.encodeToBase16()

            assertEquals(decoded.toList(), encoded.decodeFromBase16().toList())
        }
    }
}