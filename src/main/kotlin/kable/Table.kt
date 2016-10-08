package kable

import kable.Table.Entry

interface Table<R, C, out V> : Iterable<Entry<R, C, V>> {

    val entries: Set<Entry<R, C, V>>
    val rows: Set<R>
    val columns: Set<C>

    val size: Int

    val values: Collection<V>

    fun containsRow(row: R): Boolean
    fun containsColumn(column: C): Boolean
    fun contains(row: R, column: C): Boolean
    operator fun contains(value: @UnsafeVariance V): Boolean

    fun getRow(row: R): Map<C, V>
    fun getColumn(column: C): Map<R, V>
    operator fun get(row: R, column: C): V?

    fun isEmpty(): Boolean

    override fun iterator(): Iterator<Entry<R, C, V>> = entries.iterator()

    interface Entry<out R, out C, out V> {
        val row: R
        val column: C
        val value: V

        operator fun component1() = row
        operator fun component2() = column
        operator fun component3() = value
    }
}

interface MutableTable<R, C, V> : Table<R, C, V> {
    fun clear()

    fun put(row: R, column: C, value: V): V?

    fun putAll(from: Table<R, C, V>)

    fun remove(row: R, column: C): V?

    interface MutableEntry<out R, out C, V> : Entry<R, C, V> {
        override var value: V
    }
}