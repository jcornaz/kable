package kable

/**
 * Instance of a [Table] that contains one (and only one) entry
 */
class SingletonTable<R, C, V>(val entry: Table.Entry<R, C, V>) : AbstractTable<R, C, V>() {

    override val size = 1
    override val rows = setOf(entry.row)
    override val columns = setOf(entry.column)

    override val values = listOf(entry.value)
    override val entries = setOf(entry)

    override fun getRow(row: R) =
            if (row == entry.row) mapOf(entry.column to entry.value) else emptyMap()

    override fun getColumn(column: C) =
            if (column == entry.column) mapOf(entry.row to entry.value) else emptyMap()

    override fun get(row: R, column: C): V? =
            if (row == entry.row && column == entry.column) entry.value else null

    override fun isEmpty() = false

    override fun containsRow(row: R) = row == entry.row

    override fun containsColumn(column: C) = column == entry.column

    override fun containsValue(value: V) = value == entry.value

    override fun contains(row: R, column: C) = row == entry.row && column == entry.column

    override fun iterator() = object : Iterator<Table.Entry<R, C, V>> {
        var done = false

        override fun hasNext() = !done

        override fun next(): Table.Entry<R, C, V> =
                if (done) throw NoSuchElementException() else entry.also { done = true }
    }
}