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

class MutableBiKeyMap<R, C, V>(val backedMap: MutableMap<Pair<R, C>, V> = HashMap()) : AbstractTable<R, C, V>(), MutableTable<R, C, V> {

    override val rows: Set<R>
        get() = backedMap.keys.map { it.first }.toSet()

    override val columns: Set<C>
        get() = backedMap.keys.map { it.second }.toSet()

    override val values: Collection<V>
        get() = backedMap.values

    override val entries: Set<Table.Entry<R, C, V>>
        get() = backedMap.entries.map { it.toTableEntry() }.toSet()

    override val size: Int
        get() = backedMap.size

    constructor(entries: Collection<Table.Entry<R, C, V>>) : this(entries.associate { (row, column, value) -> (row to column) to value }.toMutableMap())
    constructor(table: Table<R, C, V>) : this(table.entries)

    override fun get(row: R, column: C): V? = backedMap[row to column]
    override fun getRow(row: R): Map<C, V> = backedMap.filterKeys { it.first == row }.mapKeys { it.key.second }
    override fun getColumn(column: C): Map<R, V> = backedMap.filterKeys { it.second == column }.mapKeys { it.key.first }

    override fun set(row: R, column: C, value: V) {
        backedMap[row to column] = value
    }

    override fun setRow(row: R, entries: Map<C, V>) {
        backedMap.keys.filter { it.first == row && it.second !in entries }.forEach { backedMap.remove(it) }
        entries.forEach { (column, value) -> backedMap[row to column] = value }
    }

    override fun setColumn(column: C, entries: Map<R, V>) {
        backedMap.keys.filter { it.second == column && it.first !in entries }.forEach { backedMap.remove(it) }
        entries.forEach { (row, value) -> backedMap[row to column] = value }
    }

    override fun remove(row: R, column: C): V? = backedMap.remove(row to column)

    override fun removeRow(row: R): Map<C, V> = getRow(row).also { result ->
        result.keys.forEach { backedMap.remove(row to it) }
    }

    override fun removeColumn(column: C): Map<R, V> = getColumn(column).also { result ->
        result.keys.forEach { backedMap.remove(it to column) }
    }

    override fun clear() {
        backedMap.clear()
    }

    override fun toString() = backedMap.toString()
}
