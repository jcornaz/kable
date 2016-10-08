package kable

import kable.MutableTable.MutableEntry
import java.util.*

class HashTable<R, C, V> : MutableTable<R, C, V> {

    private val rowMaps = HashMap<R, HashMap<C, SimpleEntry<R, C, V>>>()
    private val columnMaps = HashMap<C, HashMap<R, SimpleEntry<R, C, V>>>()
    private val cellSet = HashSet<SimpleEntry<R, C, V>>()

    override val rows: Set<R>
        get() = rowMaps.keys

    override val columns: Set<C>
        get() = columnMaps.keys

    override val values: Collection<V>
        get() = cellSet.map { it.value }

    override val entries: Set<MutableEntry<R, C, V>>
        get() = cellSet.toSet()

    override val size: Int
        get() = cellSet.size


    override fun getRow(row: R): Map<C, V> = rowMaps[row]?.mapValues { it.value.value }.orEmpty()
    override fun getColumn(column: C): Map<R, V> = columnMaps[column]?.mapValues { it.value.value }.orEmpty()

    override operator fun get(row: R, column: C): V? = synchronized(this) { rowMaps[row]?.get(column)?.value }

    override fun put(row: R, column: C, value: V): V? = synchronized(this) {
        val oldEntry = rowMaps[row]?.get(column)
        val oldValue = oldEntry?.value

        val entry = oldEntry ?: SimpleEntry(row, column, value)
        entry.value = value

        if (oldEntry == null) {
            rowMaps.getOrPut(row) { HashMap() }.put(column, entry)
            columnMaps.getOrPut(column) { HashMap() }.put(row, entry)
            cellSet += entry
        }

        return@synchronized oldValue
    }

    override fun putAll(from: Table<R, C, V>) = synchronized(this) {
        val cells = from.entries.map { SimpleEntry(it.row, it.column, it.value) }
        cellSet += cells
        cells.forEach {
            rowMaps.getOrPut(it.row) { HashMap() }.put(it.column, it)
            columnMaps.getOrPut(it.column) { HashMap() }.put(it.row, it)
        }
    }

    override fun remove(row: R, column: C): V? = synchronized(this) {
        rowMaps[row]?.remove(column)?.let { cellSet -= it }
        return@synchronized columnMaps[column]?.remove(row)?.value
    }

    override fun containsRow(row: R) = row in rowMaps
    override fun containsColumn(column: C) = column in columnMaps
    override fun contains(row: R, column: C) = rowMaps[row]?.let { column in it } ?: false
    override operator fun contains(value: V) = entries.any { it.value == value }

    override fun isEmpty(): Boolean = cellSet.isEmpty()

    override fun clear() = synchronized(this) {
        rowMaps.clear()
        columnMaps.clear()
        cellSet.clear()
    }
}