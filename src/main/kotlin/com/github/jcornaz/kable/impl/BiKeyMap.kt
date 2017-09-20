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

import com.github.jcornaz.kable.Table.Entry
import com.github.jcornaz.kable.util.entry
import com.github.jcornaz.kable.util.toTableEntry

/**
 * Implementation of a [com.github.jcornaz.kable.Table] backed with [Map] where keys are row-column pairs
 */
class BiKeyMap<R, C, V>(entries: Iterable<Entry<R, C, V>> = emptyList()) : AbstractTable<R, C, V>() {

    /** Map of the values by row-column pairs */
    val map: Map<Pair<R, C>, V> by lazy { entries.associate { (row, column, value) -> (row to column) to value } }

    /** Map of maps by rows, then by columns */
    val rowsMap: Map<R, Map<C, V>> by lazy {
        map.asSequence().groupBy { it.key.first }.mapValues { entry ->
            entry.value.associate {
                it.key.second to it.value
            }
        }
    }

    /** Map of maps by columns, then by rows */
    val columnsMap: Map<C, Map<R, V>> by lazy {
        map.asSequence().groupBy { it.key.second }.mapValues { entry ->
            entry.value.associate {
                it.key.first to it.value
            }
        }
    }

    override val size by lazy { map.size }

    override val rows by lazy { rowsMap.keys }
    override val columns by lazy { columnsMap.keys }
    override val values by lazy { map.values }

    override val entries by lazy { map.asSequence().map { entry(it.key.first, it.key.second, it.value) }.toSet() }

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun contains(row: R, column: C) = (row to column) in map
    override fun containsValue(value: V) = map.containsValue(value)

    override fun getRow(row: R) = rowsMap[row] ?: emptyMap()
    override fun getColumn(column: C) = columnsMap[column] ?: emptyMap()

    override fun get(row: R, column: C) = map[row to column]

    override fun iterator() = object : Iterator<Entry<R, C, V>> {
        val i = map.iterator()

        override fun hasNext() = i.hasNext()
        override fun next() = i.next().toTableEntry()
    }

    override fun toString() = map.toString()
}