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

        assertTrue(table.getRow('A').isEmpty())
        assertTrue(table.getColumn(1).isEmpty())
        assertNull(table['A', 1])
    }

    @Test fun testEquals() {
        assertEquals(emptyTable<Char, Int, String>(), emptyTable<Double, String, Boolean>())
        assertEquals(emptyTable<Char, Int, String>(), BiKeyMap<Double, String, Boolean>())
    }
}