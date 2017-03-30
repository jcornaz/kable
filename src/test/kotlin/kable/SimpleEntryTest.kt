package kable

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SimpleEntryTest {

    @Test fun testEquals() {
        assertEquals(entry('A', 1, "test"), entry('A', 1, "test"))

        assertNotEquals(entry('B', 1, "test"), entry('A', 1, "test"))
        assertNotEquals(entry('A', 2, "test"), entry('A', 1, "test"))
        assertNotEquals(entry('A', 1, "tested"), entry('A', 1, "test"))
    }
}
