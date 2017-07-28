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

interface MutableTable<R, C, V> : Table<R, C, V> {

    operator fun set(row: R, column: C, value: V)
    fun setRow(row: R, entries: Map<C, V>)
    fun setColumn(column: C, entries: Map<R, V>)

    fun remove(row: R, column: C): V?
    fun removeRow(row: R): Map<C, V>
    fun removeColumn(column: C): Map<R, V>

    fun clear()
}
