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

package kable

import kable.Table.Entry

/**
 * Implementation of a [Table] backed with [Map] where keys are row-column pairs
 */
class BiKeyMap<R, C, V>(val map: Map<Pair<R, C>, V> = emptyMap()) : AbstractTable<R, C, V>() {

    override val size by lazy { map.size }

    /** Map of maps by rows, then by columns */
    val rowsMap by lazy {
        map.entries.groupBy { it.key.first }.mapValues { entry ->
            entry.value.associate {
                it.key.second to it.value
            }
        }
    }

    /** Map of maps by columns, then by rows */
    val columnsMap by lazy {
        map.entries.groupBy { it.key.second }.mapValues { entry ->
            entry.value.associate {
                it.key.first to it.value
            }
        }
    }

    override val rows by lazy { rowsMap.keys }
    override val columns by lazy { columnsMap.keys }
    override val values by lazy { map.values }

    override val entries by lazy { map.map { entry(it.key.first, it.key.second, it.value) }.toSet() }

    constructor(entries: Collection<Entry<R, C, V>>) : this(
            entries.associate { it.toPair() }
    )

    override fun toMap() = map

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun contains(row: R, column: C) = (row to column) in map
    override fun containsValue(value: V) = map.containsValue(value)

    override fun getRow(row: R) = rowsMap[row] ?: emptyMap()
    override fun getColumn(column: C) = columnsMap[column] ?: emptyMap()

    override fun get(row: R, column: C) = get(row to column)
    operator fun get(key: Pair<R, C>) = map[key]

    override fun iterator() = object : Iterator<Entry<R, C, V>> {
        val i = map.iterator()

        override fun hasNext() = i.hasNext()
        override fun next() = i.next().let { entry(it.key.first, it.key.second, it.value) }
    }

    override fun toString() = map.toString()
}