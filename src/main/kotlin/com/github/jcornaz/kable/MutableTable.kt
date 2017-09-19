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

package com.github.jcornaz.kable

/**
 * Mutable version of a [Table]
 */
interface MutableTable<R, C, V> : Table<R, C, V> {

    /** Set the value associated with the given row and column */
    operator fun set(row: R, column: C, value: V)

    /**
     * Add the given entry
     *
     * Replace any existing entry with the same row and column value
     */
    fun put(entry: Table.Entry<R, C, V>) = set(entry.row, entry.column, entry.value)

    /**
     * Add all the given entries
     *
     * Replace any exiting entries with a row and column pair contained in the given argument
     * In case of duplicates given entries, only the last one is kept
     */
    fun putAll(entries: Iterable<Table.Entry<R, C, V>>) = entries.forEach(this::put)

    /** Set all column-values associations for the given row */
    fun setRow(row: R, entries: Map<C, V>)

    /** Set all row-values association for the given column */
    fun setColumn(column: C, entries: Map<R, V>)

    /** Remove the entry for the given row and column if any */
    fun remove(row: R, column: C): V?

    /** Remove the given row */
    fun removeRow(row: R): Map<C, V>

    /** Remove the given column */
    fun removeColumn(column: C): Map<R, V>

    /** Remove all the entries in the table */
    fun clear()
}
