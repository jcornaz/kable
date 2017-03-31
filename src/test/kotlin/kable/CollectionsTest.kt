package kable

import org.junit.Assert.assertEquals
import org.junit.Test

class CollectionsTest {

    @Test fun testCreateTableFromEntryCollection() {
        val list = listOf(
                entry('A', 1, "hello world"),
                entry('B', 4, "goodbye"),
                entry('A', 4, "bye")
        )

        val table = tableOf(
                entry('A', 1, "hello world"),
                entry('B', 4, "goodbye"),
                entry('A', 4, "bye")
        )

        assertEquals(table, list.toTable())
    }

    @Test fun testCreateTableFromMap() {
        val list = mapOf(
                ('A' to 1) to "hello world",
                ('B' to 4) to "goodbye",
                ('A' to 4) to "bye"
        )

        val table = tableOf(
                entry('A', 1, "hello world"),
                entry('B', 4, "goodbye"),
                entry('A', 4, "bye")
        )

        assertEquals(table, list.toTable())
    }
}