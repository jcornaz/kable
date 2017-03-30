package kable

import org.junit.Assert.*
import org.junit.Test

class SingletonTableTest {

    @Test fun testIsReturnByTableOf() {
        assertTrue(tableOf(entry('A', 1, "hello world")) is SingletonTable)
        assertEquals(tableOf(entry('A', 1, "hello world")), SingletonTable(entry('A', 1, "hello world")))

        assertFalse(emptyTable<Char, Int, String>() is SingletonTable)
        assertFalse(tableOf<Char, Int, String>() is SingletonTable)
        assertFalse(tableOf(entry('A', 1, "hello"), entry('B', 2, "bye")) is SingletonTable)
    }

    @Test fun testContains() {
        val table = SingletonTable(entry('A', 1, "hello world"))

        assertEquals(1, table.size)
        assertFalse(table.isEmpty())

        assertTrue(table.contains('A', 1))
        assertTrue(table.containsRow('A'))
        assertTrue(table.containsColumn(1))

        assertFalse(table.contains('A', 2))
        assertFalse(table.containsRow('B'))
        assertFalse(table.containsColumn(2))
    }

    @Test fun testGet() {
        val table = SingletonTable(entry('A', 1, "hello world"))

        assertEquals(table['A', 1], "hello world")
        assertNull(table['B', 2])

        assertEquals(mapOf(1 to "hello world"), table.getRow('A'))
        assertEquals(mapOf('A' to "hello world"), table.getColumn(1))

        assertTrue(table.getRow('B').isEmpty())
        assertTrue(table.getColumn(2).isEmpty())
    }

    @Test fun testIteration() {
        val table = SingletonTable(entry('A', 1, "hello world"))

        var i = 0

        for ((row, column, value) in table) {
            assertEquals('A', row)
            assertEquals(1, column)
            assertEquals("hello world", value)
            i++
        }

        assertEquals(i, 1)
    }

    @Test fun testEquals() {
        assertEquals(SingletonTable(entry('A', 1, "hello world")), tableOf(entry('A', 1, "hello world"), entry('B', 2, "bye")) - ('B' to 2))
        assertEquals(tableOf(entry('A', 1, "hello world"), entry('B', 2, "bye")) - ('B' to 2), SingletonTable(entry('A', 1, "hello world")))
    }
}
