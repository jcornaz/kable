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

import com.github.jcornaz.kable.Table
import com.github.jcornaz.kable.Table.Entry

/**
 * Singleton instance of an empty [Table]
 */
object EmptyTable : AbstractTable<Any?, Any?, Any?>() {

    override val size = 0
    override val rows = emptySet<Any?>()
    override val columns = emptySet<Any?>()
    override val values = emptyList<Nothing>()
    override val entries = emptySet<Entry<Any?, Any?, Any?>>()

    override fun toMap() = emptyMap<Pair<Any?, Any?>, Any?>()

    override fun isEmpty() = true

    override fun containsRow(row: Any?) = false
    override fun containsColumn(column: Any?) = false
    override fun containsValue(value: Any?) = false
    override fun contains(row: Any?, column: Any?) = false

    override fun getRow(row: Any?) = emptyMap<Any?, Any?>()
    override fun getColumn(column: Any?) = emptyMap<Any?, Any?>()
    override fun get(row: Any?, column: Any?) = null

    override fun iterator() = object : Iterator<Entry<Any?, Any?, Any?>> {
        override fun hasNext() = false
        override fun next() = throw NoSuchElementException()
    }

    override fun equals(other: Any?) = other is Table<*, *, *> && other.isEmpty()

    override fun toString() = "{}"
}