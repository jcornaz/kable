package kable.table

interface Table<R, C, V> {

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
    operator fun contains(key: Key<R, C>): Boolean = contains(key.row, key.column)

    fun getRow(row: R): Map<C, V>
    fun getColumn(column: C): Map<R, V>
    operator fun get(row: R, column: C): V?
    operator fun get(key: Key<R, C>) = get(key.row, key.column)

    fun plus(row: R, column: C, value: V): Table<R, C, V>

    operator fun plus(entry: Table.Entry<R, C, V>): Table<R, C, V> =
            plus(entry.row, entry.column, entry.value)

    operator fun plus(entries: Collection<Table.Entry<R, C, V>>): Table<R, C, V> =
            entries.fold(this) { table, entry -> table + entry }

    operator fun <R, C, V> Table<R, C, V>.plus(table: Table<R, C, V>): Table<R, C, V> =
            plus(table.entries)

    fun minusRow(row: R): Table<R, C, V> = columns.fold(this) { table, column -> minus(row, column) }
    fun minusColumn(column: C): Table<R, C, V> = rows.fold(this) { table, row -> minus(row, column) }
    fun minus(row: R, column: C): Table<R, C, V>
    operator fun minus(key: Key<R, C>): Table<R, C, V> = minus(key.row, key.column)

    operator fun iterator(): Iterator<Entry<R, C, V>> = entries.iterator()

    interface Key<out R, out C> {
        val row: R
        val column: C

        operator fun component1() = row
        operator fun component2() = column
    }

    interface Entry<out R, out C, out V> : Key<R, C> {
        val value: V

        operator fun component3() = value
    }
}