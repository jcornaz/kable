package kable

import org.junit.Assert.*
import org.junit.Test

class TableHelperTest {

    @Test fun testPlus() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        ) + entry('C', 4, "good")

        assertEquals(4, table.size)
        assertEquals("good", table['C', 4])
    }

    @Test fun testErase() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        ) + entry('A', 4, "good")

        assertEquals(3, table.size)
        assertEquals("good", table['A', 4])
    }

    @Test fun testMinus() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        ) - ('A' to 4)

        assertEquals(2, table.size)
        assertNull(table['A', 4])
    }

    @Test fun testMinusRow() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        ).minusRow('A')

        assertEquals(1, table.size)
        assertFalse(table.containsRow('A'))
        assertNull(table['A', 1])
    }

    @Test fun testMinusColumn() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        ).minusColumn(4)

        assertEquals(1, table.size)
        assertFalse(table.containsColumn(4))
        assertNull(table['A', 4])
    }

    @Test fun testMinusWhenNotContained() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        assertSame(table, table - ('B' to 1))
        assertSame(table, table.minusRow('z'))
        assertSame(table, table.minusColumn(0))
    }
}