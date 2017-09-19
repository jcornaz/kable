package kable

import org.junit.Assert
import org.junit.Test

class ThreeMapTableTest : TableTest() {

    override fun <R, C, V> createTable(vararg entries: Table.Entry<R, C, V>) = ThreeMapTable(entries.toList())

    @Test fun testSet() {
        val table = createTable(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        table['A', 4] = "new"
        table['C', 4] = "something"
        table['D', 8] = "anything"

        Assert.assertEquals(tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "new"),
                entry('C', 4, "something"),
                entry('D', 8, "anything")
        ), table)
    }

    @Test fun testSetRow() {
        val table = createTable(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        table.setRow('B', mapOf(1 to "test", 42 to "the answer"))
        table.setRow('C', mapOf(7 to "seven"))
        table.setRow('X', emptyMap()) // should have no effect

        Assert.assertEquals(tableOf(
                entry('A', 1, "hello"),
                entry('B', 1, "test"),
                entry('B', 42, "the answer"),
                entry('A', 4, "bye"),
                entry('C', 7, "seven")
        ), table)
    }

    @Test fun testSetColumn() {
        val table = createTable(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        table.setColumn(1, mapOf('X' to "axe1", 'Y' to "axe2"))
        table.setColumn(5, mapOf('A' to "yellow"))
        table.setColumn(42, emptyMap()) // should have no effect

        Assert.assertEquals(tableOf(
                entry('A', 4, "bye"),
                entry('B', 4, "world"),
                entry('X', 1, "axe1"),
                entry('Y', 1, "axe2"),
                entry('A', 5, "yellow")
        ), table)
    }

    @Test fun testRemoveEntry() {
        val table = createTable(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        table.remove('B', 1)

        Assert.assertEquals(tableOf(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        ), table)
    }

    @Test fun testRemoveRow() {
        val table = createTable(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        table.removeRow('A')

        Assert.assertEquals(tableOf(
                entry('B', 4, "world")
        ), table)
    }

    @Test fun testRemoveColumn() {
        val table = createTable(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        table.removeColumn(4)

        Assert.assertEquals(tableOf(
                entry('A', 1, "hello")
        ), table)
    }

    @Test fun testClear() {
        val table = createTable(
                entry('A', 1, "hello"),
                entry('B', 4, "world"),
                entry('A', 4, "bye")
        )

        table.clear()

        Assert.assertTrue(table.isEmpty())
    }
}
