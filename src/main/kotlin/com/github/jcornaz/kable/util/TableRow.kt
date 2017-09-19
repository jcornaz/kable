package com.github.jcornaz.kable.util

import com.github.jcornaz.kable.MutableTable
import com.github.jcornaz.kable.Table

/** Represent a table row */
data class TableRow<out R, out C>(
        val key: R,
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
