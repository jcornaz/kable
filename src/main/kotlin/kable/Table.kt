package kable

interface Table<R, C, V> {

    val factory: TableFactory

    val size: Int

    val rows: Set<R>
    val columns: Set<C>
    val values: Collection<V>
    val entries: Set<Entry<R, C, V>>

    fun isEmpty(): Boolean = size == 0

    fun containsRow(row: R): Boolean = row in rows
    fun containsColumn(column: C): Boolean = column in columns
    fun containsValue(value: V): Boolean = value in values
    fun contains(row: R, column: C): Boolean = get(row, column) != null

    fun getRow(row: R): Map<C, V>
    fun getColumn(column: C): Map<R, V>
    operator fun get(row: R, column: C): V?

    operator fun iterator(): Iterator<Entry<R, C, V>> = entries.iterator()

    interface Entry<out R, out C, out V> {
        val row: R
        val column: C
        val value: V

        operator fun component1() = row
        operator fun component2() = column
        operator fun component3() = value
    }
}