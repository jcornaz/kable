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

/**
 * Synchronize all access to the backed map, making it thread safe
 */
class SynchronizedTable<R, C, V>(private val backedTable: MutableTable<R, C, V>) : MutableTable<R, C, V> {

    override val size: Int
        @Synchronized get() = backedTable.size

    override val rows: Set<R>
        @Synchronized get() = backedTable.rows

    override val columns: Set<C>
        @Synchronized get() = backedTable.columns

    override val values: Collection<V>
        @Synchronized get() = backedTable.values

    override val entries: Set<Table.Entry<R, C, V>>
        @Synchronized get() = backedTable.entries

    @Synchronized
    override fun set(row: R, column: C, value: V) = backedTable.set(row, column, value)

    @Synchronized
    override fun put(entry: Table.Entry<R, C, V>) = backedTable.put(entry)

    @Synchronized
    override fun putAll(entries: Iterable<Table.Entry<R, C, V>>) = backedTable.putAll(entries)

    @Synchronized
    override fun setRow(row: R, entries: Map<C, V>) = backedTable.setRow(row, entries)

    @Synchronized
    override fun setColumn(column: C, entries: Map<R, V>) = backedTable.setColumn(column, entries)

    @Synchronized
    override fun remove(row: R, column: C) = backedTable.remove(row, column)

    @Synchronized
    override fun removeRow(row: R) = backedTable.removeRow(row)

    @Synchronized
    override fun removeColumn(column: C) = backedTable.removeColumn(column)

    @Synchronized
    override fun remove(key: Pair<R, C>) = backedTable.remove(key)

    @Synchronized
    override fun removeAll(keys: Iterable<Pair<R, C>>) = backedTable.removeAll(keys)

    @Synchronized
    override fun clear() = backedTable.clear()

    @Synchronized
    override fun isEmpty() = backedTable.isEmpty()

    @Synchronized
    override fun containsRow(row: R) = backedTable.containsRow(row)

    @Synchronized
    override fun containsColumn(column: C) = backedTable.containsColumn(column)

    @Synchronized
    override fun containsValue(value: V) = backedTable.containsValue(value)

    @Synchronized
    override fun contains(row: R, column: C) = backedTable.contains(row, column)

    @Synchronized
    override fun getRow(row: R) = backedTable.getRow(row)

    @Synchronized
    override fun getColumn(column: C) = backedTable.getColumn(column)

    @Synchronized
    override fun get(row: R, column: C) = backedTable[row, column]

    @Synchronized
    override fun iterator() = backedTable.iterator()

    @Synchronized
    override fun hashCode() = backedTable.hashCode()

    @Synchronized
    override fun equals(other: Any?) = backedTable == other

    @Synchronized
    override fun toString() = backedTable.toString()
}
