package kable

import org.junit.Assert.*
import org.junit.Test

class EmptyTableTest {

    @Test fun testSingleton() {
        assertSame(emptyTable<Any, Any, Any>(), emptyTable<Any, Any, Any>())
        assertSame(emptyTable<Char, Int, String>(), emptyTable<Double, String, Boolean>())
    }

    @Test fun testIsEmpty() {
        val table = emptyTable<Char, Int, String>()

        assertTrue(table.isEmpty())
        assertEquals(0, table.size)

        assertTrue(table.rows.isEmpty())
        assertTrue(table.columns.isEmpty())
        assertTrue(table.values.isEmpty())
        assertTrue(table.entries.isEmpty())

        assertFalse(table.contains('A', 1))
        assertFalse(table.containsRow('A'))
        assertFalse(table.containsColumn(1))
        assertFalse(table.containsValue(""))

        assertTrue(table.getRow('A').isEmpty())
        assertTrue(table.getColumn(1).isEmpty())
        assertNull(table['A', 1])
    }

    @Test fun testEquals() {
        assertEquals(emptyTable<Char, Int, String>(), tableOf<Char, Int, String>())
        assertEquals(emptyTable<Char, Int, String>(), tableOf(entry('A', 1, "test")) - ('A' to 1))
        assertEquals(tableOf(entry('A', 1, "test")) - ('A' to 1), emptyTable<Char, Int, String>())
    }

    @Test fun testIteration() {
        for(entry in emptyTable<Char, Int, String>())
            fail()
    }
}