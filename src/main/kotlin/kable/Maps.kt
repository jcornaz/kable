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

@file:JvmName("Maps")
package kable

/** Return a [Table] corresponding to the map where keys will be split into rows ans columns */
fun <R, C, V> Map<Pair<R, C>, V>.toTable(): Table<R, C, V> = when {
    isEmpty() -> emptyTable()
    size == 1 -> entries.first().let { (key, value) -> entry(key.first, key.second, value) }.let { tableOf(it) }
    else -> BiKeyMap(this)
}

/** Return a [Table.Entry] equivalent to this [Map.Entry] */
fun <R, C, V> Map.Entry<Pair<R, C>, V>.toTableEntry(): Table.Entry<R, C, V> =
        entry(key.first, key.second, value)