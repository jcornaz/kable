package kable

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class TablesTest {

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

    @Test fun testForEach() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        var entries = emptySet<Table.Entry<Char, Int, String>>()
        var i = 0

        table.forEach {
            entries += it
            i++
        }

        assertEquals(i, 3)
        assertEquals(setOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        ), entries)
    }

    @Test fun testAll() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        var i = 0
        assertTrue(table.all { i++ ; it.column > 0 })
        assertEquals(i, 3)

        i = 0
        assertFalse(table.all { i++ ; it.column < 3 })
        assertTrue(i < 3)
    }

    @Test fun testAny() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        assertTrue(table.any())
        assertFalse(emptyTable<Char, Int, String>().any())

        var i = 0
        assertTrue(table.any { i++ ; it.column > 3 })
        assertTrue(i < 3)

        i = 0
        assertFalse(table.any { i++ ; it.column < 0 })
        assertEquals(3, i)
    }

    @Test fun testNone() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        assertFalse(table.none())
        assertTrue(emptyTable<Char, Int, String>().none())

        var i = 0
        assertFalse(table.none { i++ ; it.column > 3 })
        assertTrue(i < 3)

        i = 0
        assertTrue(table.none { i++ ; it.column < 0 })
        assertEquals(3, i)
    }

    @Test fun testCount() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        assertEquals(3, table.count())
        assertEquals(2, table.count { it.column > 3 })
        assertEquals(1, table.count { it.column < 3 })
    }

    @Test fun testMax() {
        val table = tableOf(
                entry('A', 1, "hello world"),
                entry('B', 4, "goodbye"),
                entry('A', 4, "bye")
        )

        assertEquals(entry('B', 4, "goodbye"), table.maxBy { it.row })
        assertEquals(entry('A', 1, "hello world"), table.maxWith(Comparator { o1, o2 -> o1.value.length - o2.value.length }))
    }

    @Test fun testMin() {
        val table = tableOf(
                entry('A', 1, "hello world"),
                entry('B', 4, "goodbye"),
                entry('A', 4, "bye")
        )

        assertEquals(entry('A', 1, "hello world"), table.minBy { it.column })
        assertEquals(entry('A', 4, "bye"), table.minWith(Comparator { o1, o2 -> o1.value.length - o2.value.length }))
    }
}