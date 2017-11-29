/**
 * Copyright 2017 Jonathan Cornaz
 *
 * This file is part of Kable.
 *
 * Kable is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kable is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Kable.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.jcornaz.kable.impl

import com.github.jcornaz.kable.MutableTable
import com.github.jcornaz.kable.Table
import com.github.jcornaz.kable.util.entry
import com.github.jcornaz.kable.util.toTableEntry

/**
 * Implementation of a [MutableTable] with 3 backed mutable map.
 *
 * The backed maps are :
 * * map of values by row-column pairs
 * * map of row-value maps by columns
 * * map of column-value maps by rows
 */
class ThreeMapTable<R, C, V>(entries: Iterable<Table.Entry<R, C, V>> = emptyList()) : AbstractTable<R, C, V>(), MutableTable<R, C, V> {

    private val map = mutableMapOf<Pair<R, C>, V>()
    private val rowsMap = mutableMapOf<R, MutableMap<C, V>>()
    private val columnsMap = mutableMapOf<C, MutableMap<R, V>>()

    override val rows: Set<R>
        get() = rowsMap.keys

    override val columns: Set<C>
        get() = columnsMap.keys

    override val values: Collection<V>
        get() = map.values

    override val entries: Set<Table.Entry<R, C, V>>
        get() = map.asSequence().map { it.toTableEntry() }.toSet()

    override val size: Int
        get() = map.size

    init {
        putAll(entries)
    }

    override fun get(row: R, column: C): V? = map[row to column]
    override fun getRow(row: R): Map<C, V> = rowsMap[row].orEmpty()
    override fun getColumn(column: C): Map<R, V> = columnsMap[column].orEmpty()

    override fun set(row: R, column: C, value: V) {
        map[row to column] = value
        rowsMap.getOrPut(row) { mutableMapOf() }.put(column, value)
        columnsMap.getOrPut(column) { mutableMapOf() }.put(row, value)
    }

    override fun setRow(row: R, entries: Map<C, V>) {
        if (entries.isEmpty()) {
            removeRow(row)
        } else {
            map.keys.filter { it.first == row && it.second !in entries }.forEach { map.remove(it) }
            rowsMap[row]?.let { rowMap ->
                rowMap.keys.filter { it !in entries }.forEach { rowMap.remove(it) }
            }
            columnsMap.asSequence().filter { it.key !in entries }.map { it.value }.forEach { it.remove(row) }

            putAll(entries.asSequence().map { (column, value) -> entry(row, column, value) }.asIterable())
        }
    }

    override fun setColumn(column: C, entries: Map<R, V>) {
        if (entries.isEmpty()) {
            removeColumn(column)
        } else {
            map.keys.filter { it.second == column && it.first !in entries }.forEach { map.remove(it) }
            columnsMap[column]?.let { columnMap ->
                columnMap.keys.filter { it !in entries }.forEach { columnMap.remove(it) }
            }
            rowsMap.asSequence().filter { it.key !in entries }.map { it.value }.forEach { it.remove(column) }

            putAll(entries.asSequence().map { (row, value) -> entry(row, column, value) }.asIterable())
        }
    }

    override fun remove(row: R, column: C): V? {
        rowsMap[row]?.remove(column)
        columnsMap[column]?.remove(row)
        return map.remove(row to column)
    }

    override fun removeRow(row: R): Map<C, V> {
        map.keys.filter { it.first == row }.forEach { map.remove(it) }
        columnsMap.asSequence().map { it.value }.forEach { it.remove(row) }
        return rowsMap.remove(row).orEmpty()
    }

    override fun removeColumn(column: C): Map<R, V> {
        map.keys.filter { it.second == column }.forEach { map.remove(it) }
        rowsMap.asSequence().map { it.value }.forEach { it.remove(column) }
        return columnsMap.remove(column).orEmpty()
    }

    override fun clear() {
        map.clear()
        rowsMap.clear()
        columnsMap.clear()
    }

    override fun toString() = map.toString()
}
