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
        get() = synchronized(this) { backedTable.size }

    override val rows: Set<R>
        get() = synchronized(this) { backedTable.rows }

    override val columns: Set<C>
        get() = synchronized(this) { backedTable.columns }

    override val values: Collection<V>
        get() = synchronized(this) { backedTable.values }

    override val entries: Set<Table.Entry<R, C, V>>
        get() = synchronized(this) { backedTable.entries }

    override fun set(row: R, column: C, value: V) = synchronized(this) { backedTable.set(row, column, value) }

    override fun put(entry: Table.Entry<R, C, V>) = synchronized(this) { backedTable.put(entry) }
    override fun putAll(entries: Iterable<Table.Entry<R, C, V>>) = synchronized(this) { backedTable.putAll(entries) }

    override fun setRow(row: R, entries: Map<C, V>) = synchronized(this) { backedTable.setRow(row, entries) }
    override fun setColumn(column: C, entries: Map<R, V>) = synchronized(this) { backedTable.setColumn(column, entries) }

    override fun remove(row: R, column: C) = synchronized(this) { backedTable.remove(row, column) }
    override fun removeRow(row: R) = synchronized(this) { backedTable.removeRow(row) }
    override fun removeColumn(column: C) = synchronized(this) { backedTable.removeColumn(column) }
    override fun remove(key: Pair<R, C>) = synchronized(this) { backedTable.remove(key) }
    override fun removeAll(keys: Iterable<Pair<R, C>>) = synchronized(this) { backedTable.removeAll(keys) }


    override fun clear() = synchronized(this) { backedTable.clear() }

    override fun isEmpty() = synchronized(this) { backedTable.isEmpty() }

    override fun containsRow(row: R) = synchronized(this) { backedTable.containsRow(row) }
    override fun containsColumn(column: C) = synchronized(this) { backedTable.containsColumn(column) }
    override fun containsValue(value: V) = synchronized(this) { backedTable.containsValue(value) }
    override fun contains(row: R, column: C) = synchronized(this) { backedTable.contains(row, column) }

    override fun getRow(row: R) = synchronized(this) { backedTable.getRow(row) }
    override fun getColumn(column: C) = synchronized(this) { backedTable.getColumn(column) }

    override fun get(row: R, column: C) = synchronized(this) { backedTable[row, column] }

    override fun iterator() = synchronized(this) { backedTable.iterator() }

    override fun hashCode() = synchronized(this) { backedTable.hashCode() }
    override fun equals(other: Any?) = synchronized(this) { backedTable == other }

    override fun toString() = synchronized(this) { backedTable.toString() }
}
