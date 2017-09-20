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

package com.github.jcornaz.kable.util

import com.github.jcornaz.kable.MutableTable
import com.github.jcornaz.kable.Table

/** Represent a table row */
data class TableRow<out R, out C>(

        /** Key of the row */
        val key: R,

        /** Values of the row in the same order as the column are given */
        val values: List<C>
)

/** Return a row representation of the table */
fun <R, V> row(row: R, vararg values: V) = TableRow(row, values.toList())

/** Return a list of columns */
fun <C> columns(vararg columns: C): Array<out C> = columns

/** Create a table with the given columns and rows */
fun <R, C, V> tableOf(columns: Array<out C>, vararg rows: TableRow<R, V>): Table<R, C, V> =
        rows.flatMap { row ->
            row.values.mapIndexed { i, value ->
                val column = columns.getOrNull(i) ?: throw IllegalArgumentException("The number of columns doesn't math the lenght of the rows")
                entry(row.key, column, value)
            }
        }.toTable()

/** Create a mutable table with the given columns and rows */
fun <R, C, V> mutableTableOf(columns: Array<out C>, vararg rows: TableRow<R, V>): MutableTable<R, C, V> = tableOf(columns, *rows).toMutableTable()
