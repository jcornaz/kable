package kable

import org.junit.Assert.*
import org.junit.Test

class BiKeyMapTest {

    @Test fun testEmpty() {
        val table = emptyTable<Int, Char, String>()

        assertTrue(table.isEmpty())
        assertTrue(table.rows.isEmpty())
        assertTrue(table.columns.isEmpty())
        assertTrue(table.values.isEmpty())
        assertTrue(table.entries.isEmpty())
    }

    @Test fun testContent() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        assertFalse(table.isEmpty())
        assertEquals(setOf('A', 'B'), table.rows)
        assertEquals(setOf(1, 4), table.columns)
        assertEquals(setOf("hello", "world", "bye"), table.values.toSet())
        assertEquals(setOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        ), table.entries)
    }

    @Test fun testContains() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        assertTrue(table.containsRow('A'))
        assertTrue(table.containsColumn(4))
        assertTrue(table.contains('A', 1))

        assertFalse(table.containsRow('Z'))
        assertFalse(table.containsColumn(0))
        assertFalse(table.contains('B', 1))
    }

    @Test fun testGet() {
        val table = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        assertEquals(mapOf(1 to "hello", 4 to "bye"), table.getRow('A'))
        assertEquals(mapOf('A' to "bye", 'B' to "world"), table.getColumn(4))
        assertEquals("hello", table['A', 1])

        assertEquals(emptyMap<Int, String>(), table.getRow('Z'))
        assertEquals(emptyMap<Int, String>(), table.getColumn(0))
        assertNull(table['B', 1])
    }

    @Test fun testIteration() {
        var entries = setOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        val table = tableOf(entries)

        for ((row, column, value) in table) {
            entry(row, column, value).let {
                assertTrue(it in entries)
                entries -= it
            }
        }

        assertTrue(entries.isEmpty())
    }

    @Test fun testEquals() {
        val table1 = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        val table2 = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        val table3 = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 1, "bye")
        )

        val table4 = tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye"),
                entry('B', 1, "test")
        )

        assertEquals(table1, table2)
        assertNotEquals(table1, table3)
        assertNotEquals(table2, table4)
    }
}