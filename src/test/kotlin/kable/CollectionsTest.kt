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

    @Test fun testCreateTableWithGroupBy() {
        val list = listOf("hello world", "goodbye", "bye", "bee")

        val table = tableOf(
                entry('h', 11, listOf("hello world")),
                entry('g', 7, listOf("goodbye")),
                entry('b', 3, listOf("bye", "bee"))
        )

        assertEquals(table, list.groupTableBy { it.first() to it.length })
    }

    @Test fun testCreateTableWithGroupByWithValueTransform() {
        val list = listOf("hello world", "goodbye", "bye", "bee")

        val table = tableOf(
                entry('h', 11, listOf("dlrow olleh")),
                entry('g', 7, listOf("eybdoog")),
                entry('b', 3, listOf("eyb", "eeb"))
        )

        assertEquals(table, list.groupTableBy({ it.first() to it.length }, String::reversed))
    }

    @Test fun testCreateTableWithAssociate() {
        val list = listOf("hello world", "goodbye", "bye")

        val table = tableOf(
                entry('h', 11, false),
                entry('g', 7, false),
                entry('b', 3, true)
        )

        assertEquals(table, list.associateTable { entry(it.first(), it.length, it.length < 4) })
    }

    @Test fun testCreateTableWithAssociateBy() {
        val list = listOf("hello world", "goodbye", "bye")

        val table = tableOf(
                entry('h', 11, "hello world"),
                entry('g', 7, "goodbye"),
                entry('b', 3, "bye")
        )

        assertEquals(table, list.associateTableBy { it.first() to it.length })
    }

    @Test fun testCreateTableWithAssociateByWithValueTransform() {
        val list = listOf("hello world", "goodbye", "bye")

        val table = tableOf(
                entry('h', 11, "dlrow olleh"),
                entry('g', 7, "eybdoog"),
                entry('b', 3, "eyb")
        )

        assertEquals(table, list.associateTableBy({ it.first() to it.length }, String::reversed))
    }
}