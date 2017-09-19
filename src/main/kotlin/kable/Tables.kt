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

@file:JvmName("Tables")

package kable

import kable.Table.Entry
import java.util.*

/** Create an empty table */
fun <R, C, V> emptyTable(): Table<R, C, V> = @Suppress("UNCHECKED_CAST") (EmptyTable as Table<R, C, V>)

/** Create a table entry with the given row, column and value */
fun <R, C, V> entry(row: R, column: C, value: V): Entry<R, C, V> = SimpleTableEntry(row, column, value)

/** Create an empty table */
fun <R, C, V> tableOf(): Table<R, C, V> = emptyTable()

/** Create a table with a [map] of entries */
fun <R, C, V> tableOf(map: Map<Pair<R, C>, V>) = map.toTable()

/** Create a table with the given entries */
fun <R, C, V> tableOf(entries: Collection<Entry<R, C, V>>): Table<R, C, V> = when {
    entries.isEmpty() -> emptyTable()
    entries.size == 1 -> tableOf(entries.first())
    else -> BiKeyMap(entries)
}

/** Create a singleton table with the given entry */
fun <R, C, V> tableOf(entry: Entry<R, C, V>): Table<R, C, V> = SingletonTable(entry)

/** Create a table with the given entries */
fun <R, C, V> tableOf(vararg entries: Entry<R, C, V>): Table<R, C, V> = tableOf(entries.toList())

/** Create a mutable table with the given entries */
fun <R, C, V> mutableTableOf(vararg entries: Entry<R, C, V>): MutableTable<R, C, V> = entries.asIterable().toMutableTable()

/** Create a copy of this table */
fun <R, C, V> Table<R, C, V>.toTable(): Table<R, C, V> = entries.toTable()

/** Create a mutable copy of this table */
fun <R, C, V> Table<R, C, V>.toMutableTable(): MutableTable<R, C, V> = entries.toMutableTable()

/** Return true if, and only if, the table contains the given row-column key pair */
operator fun <R, C> Table<R, C, *>.contains(key: Pair<R, C>) = contains(key.first, key.second)

/**
 * Return all the values of the row mapped by column
 *
 * @see Table.getRow
 */
operator fun <R, C, V> Table<R, C, V>.get(row: R): Map<C, V> = getRow(row)

/** Return a new table with the same entries plus the given new entry */
operator fun <R, C, V> Table<R, C, V>.plus(entry: Entry<R, C, V>): Table<R, C, V> =
        tableOf(toMap() + entry.toPair())

/** Return a new table with the same entries plus the given new entries */
operator fun <R, C, V> Table<R, C, V>.plus(entries: Collection<Entry<R, C, V>>): Table<R, C, V> =
        tableOf(toMap() + entries.associate { it.toPair() })

/** Return a new table with the same entries plus entries of the given table */
operator fun <R, C, V> Table<R, C, V>.plus(table: Table<R, C, V>): Table<R, C, V> =
        tableOf(toMap() + table.toMap())

/** Return a new table with the same entries except the given row */
fun <R, C, V> Table<R, C, V>.minusRow(row: R): Table<R, C, V> =
        if (!containsRow(row)) this
        else toMap().filterNot { (key, _) -> key.first == row }.toTable()

/** Return a new table with the same entries except the given column */
fun <R, C, V> Table<R, C, V>.minusColumn(column: C): Table<R, C, V> =
        if (!containsColumn(column)) this
        else toMap().filterNot { (key, _) -> key.second == column }.toTable()

/** Return a new table with the same entries except the row-column pair */
operator fun <R, C, V> Table<R, C, V>.minus(key: Pair<R, C>): Table<R, C, V> =
        if (key !in this) this
        else toMap().filterNot { (k, _) -> k.first == key.first && k.second == key.second }.toTable()

/** Performs the given [action] on each entry */
inline fun <R, C, V> Table<R, C, V>.forEach(action: (Entry<R, C, V>) -> Unit): Unit =
        entries.forEach(action)

/** Returns `true` if all entries match the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.all(predicate: (Entry<R, C, V>) -> Boolean): Boolean =
        toMap().all { predicate(it.toTableEntry()) }

/** Returns `true` if map has at least one entry */
fun Table<*, *, *>.any(): Boolean = toMap().any()

/** Returns `true` if at least one entry matches the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.any(predicate: (Entry<R, C, V>) -> Boolean): Boolean =
        toMap().any { predicate(it.toTableEntry()) }

/** Returns `true` if the map has no entries */
fun Table<*, *, *>.none(): Boolean = toMap().none()

/** Returns `true` if no entry match the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.none(predicate: (Entry<R, C, V>) -> Boolean): Boolean =
        toMap().none { predicate(it.toTableEntry()) }

/** Returns the number of entries in this map */
fun Table<*, *, *>.count(): Int = size

/** Returns the number of entries matching the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.count(predicate: (Entry<R, C, V>) -> Boolean): Int =
        toMap().count { predicate(it.toTableEntry()) }

/** Returns the first entry yielding the largest value of the given function or `null` if there are no entries */
inline fun <R, C, V, T : Comparable<T>> Table<R, C, V>.maxBy(selector: (Entry<R, C, V>) -> T): Entry<R, C, V>? =
        entries.maxBy(selector)

/** Returns the first entry having the largest value according to the provided [comparator] or `null` if there are no entries */
fun <R, C, V> Table<R, C, V>.maxWith(comparator: Comparator<Entry<R, C, V>>): Entry<R, C, V>? =
        entries.maxWith(comparator)

/** Returns the first entry yielding the smallest value of the given function or `null` if there are no entries */
inline fun <R, C, V, T : Comparable<T>> Table<R, C, V>.minBy(selector: (Entry<R, C, V>) -> T): Entry<R, C, V>? =
        entries.minBy(selector)

/** Returns the first entry having the smallest value according to the provided [comparator] or `null` if there are no entries */
fun <R, C, V> Table<R, C, V>.minWith(comparator: Comparator<Entry<R, C, V>>): Entry<R, C, V>? =
        entries.minWith(comparator)

/** Returns a new table containing all entries matching the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.filter(predicate: (Entry<R, C, V>) -> Boolean): Table<R, C, V> =
        toMap().filter { predicate(it.toTableEntry()) }.toTable()

/** Returns a new table containing all entries not matching the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.filterNot(predicate: (Entry<R, C, V>) -> Boolean): Table<R, C, V> =
        toMap().filterNot { predicate(it.toTableEntry()) }.toTable()

/** Returns a new table containing all entries on rows matching the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.filterRows(predicate: (R) -> Boolean): Table<R, C, V> =
        toMap().filterKeys { predicate(it.first) }.toTable()

/** Returns a new table containing all entries on columns matching the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.filterColumns(predicate: (C) -> Boolean): Table<R, C, V> =
        toMap().filterKeys { predicate(it.second) }.toTable()

/** Returns a new table containing all entries with a values matching the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.filterValues(predicate: (V) -> Boolean): Table<R, C, V> =
        toMap().filterValues(predicate).toTable()

/** Returns a list containing the results of applying the given [transform] function to each entry in the original table */
inline fun <R, C, V, T> Table<R, C, V>.map(transform: (Entry<R, C, V>) -> T): Collection<T> =
        toMap().map { transform(it.toTableEntry()) }

/** Returns a list containing the results of applying the given [transform] function to each entry in the original table */
inline fun <R, C, V, T> Table<R, C, V>.flatMap(transform: (Entry<R, C, V>) -> Iterable<T>): Collection<T> =
        toMap().flatMap { transform(it.toTableEntry()) }

/** Returns a list containing only the non-null results of applying the given [transform] function to each entry in the original table */
inline fun <R, C, V, T : Any> Table<R, C, V>.mapNotNull(transform: (Entry<R, C, V>) -> T?): Collection<T> =
        toMap().mapNotNull { transform(it.toTableEntry()) }

/** Returns a new table with entries having the rows obtained by applying the [transform] function to each entry */
inline fun <R, C, V, T> Table<R, C, V>.mapRows(transform: (Entry<R, C, V>) -> T): Table<T, C, V> =
        toMap().mapKeys { transform(it.toTableEntry()) to it.key.second }.toTable()

/** Returns a new table with entries having the columns obtained by applying the [transform] function to each entry */
inline fun <R, C, V, T> Table<R, C, V>.mapColumns(transform: (Entry<R, C, V>) -> T): Table<R, T, V> =
        toMap().mapKeys { it.key.first to transform(it.toTableEntry()) }.toTable()

/** Returns a new table with entries having the values obtained by applying the [transform] function to each entry */
inline fun <R, C, V, T> Table<R, C, V>.mapValues(transform: (Entry<R, C, V>) -> T): Table<R, C, T> =
        toMap().mapValues { transform(it.toTableEntry()) }.toTable()
