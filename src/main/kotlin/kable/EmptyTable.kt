package kable

import kable.Table.Entry

/**
 * Singleton instance of an empty [Table]
 */
object EmptyTable : AbstractTable<Any?, Any?, Any?>() {

    override val size = 0
    override val rows = emptySet<Any?>()
    override val columns = emptySet<Any?>()
    override val values = emptyList<Nothing>()
    override val entries = emptySet<Entry<Any?, Any?, Any?>>()

    override fun isEmpty() = true

    override fun containsRow(row: Any?) = false
    override fun containsColumn(column: Any?) = false
    override fun containsValue(value: Any?) = false
    override fun contains(row: Any?, column: Any?) = false

    override fun getRow(row: Any?) = emptyMap<Any?, Any?>()
    override fun getColumn(column: Any?) = emptyMap<Any?, Any?>()
    override fun get(row: Any?, column: Any?) = null

    override fun iterator() = object : Iterator<Entry<Any?, Any?, Any?>> {
        override fun hasNext() = false
        override fun next() = throw NoSuchElementException()
    }

    override fun equals(other: Any?) = other is Table<*, *, *> && other.isEmpty()
    override fun hashCode() = emptySet<Any?>().hashCode()
}