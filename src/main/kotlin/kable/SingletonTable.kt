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
 * Instance of a [Table] that contains one (and only one) entry
 */
class SingletonTable<R, C, V>(val entry: Entry<R, C, V>) : AbstractTable<R, C, V>() {

    override val size = 1
    override val rows = setOf(entry.row)
    override val columns = setOf(entry.column)

    override val values = listOf(entry.value)
    override val entries = setOf(entry)

    override fun toMap() = mapOf(entry.toPair())

    override fun getRow(row: R) =
            if (row == entry.row) mapOf(entry.column to entry.value) else emptyMap()

    override fun getColumn(column: C) =
            if (column == entry.column) mapOf(entry.row to entry.value) else emptyMap()

    override fun get(row: R, column: C): V? =
            if (row == entry.row && column == entry.column) entry.value else null

    override fun isEmpty() = false

    override fun containsRow(row: R) = row == entry.row

    override fun containsColumn(column: C) = column == entry.column

    override fun containsValue(value: V) = value == entry.value

    override fun contains(row: R, column: C) = row == entry.row && column == entry.column

    override fun iterator() = object : Iterator<Entry<R, C, V>> {
        var done = false

        override fun hasNext() = !done

        override fun next(): Entry<R, C, V> =
                if (done) throw NoSuchElementException() else entry.also { done = true }
    }

    override fun toString() = "{(${entry.row}, ${entry.column})=${entry.value}}"
}