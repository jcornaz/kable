package kable.encoding

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigInteger
import java.util.*

class HexadecimalTest {

    val rng = Random()

    fun Random.nextByteArray(size: Int) = ByteArray(size).apply { this@nextByteArray.nextBytes(this) }

    @Test
    fun testEncodedFormat() {
        (1..1000).forEach {
            assertTrue(Regex("^[0-9a-f]{32}$").matches(rng.nextByteArray(16).encodeToHexadecimal()))
        }
    }

    @Test
    fun testEncodeDecode() {
        (1..1000).forEach {
            val decoded: ByteArray = rng.nextByteArray(16)
            val encoded: String = decoded.encodeToHexadecimal()

            assertEquals(decoded.toList(), encoded.decodeFromHexadecimal().toList())
        }
    }

    @Test
    fun testPerformance() {

        val startDate = Date().time

        (1..1000000).forEach {
            BigInteger(128, rng).toString(16)
        }

        val midDate = Date().time

        (1..1000000).forEach {
            BigInteger(218, rng).toByteArray().encodeToHexadecimal()
        }

        val endDate = Date().time

        // It should be al least twice faster than BigInteger.toString()
        Assert.assertTrue((endDate - midDate).toDouble() / (midDate - startDate).toDouble() < 0.5)
    }
}