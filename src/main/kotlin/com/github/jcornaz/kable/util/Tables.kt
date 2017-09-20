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

package com.github.jcornaz.kable.util

import com.github.jcornaz.kable.MutableTable
import com.github.jcornaz.kable.Table
import com.github.jcornaz.kable.Table.Entry
import com.github.jcornaz.kable.impl.*

/** Create an empty table */
fun <R, C, V> emptyTable(): Table<R, C, V> = @Suppress("UNCHECKED_CAST") (EmptyTable as Table<R, C, V>)

/** Create a table entry with the given row, column and value */
fun <R, C, V> entry(row: R, column: C, value: V): Entry<R, C, V> = SimpleTableEntry(row, column, value)

/** Create a table with the given entries */
fun <R, C, V> tableOf(entries: Collection<Entry<R, C, V>>): Table<R, C, V> = when {
    entries.isEmpty() -> emptyTable()
    entries.size == 1 -> tableOf(entries.first())
    else -> BiKeyMap(entries)
}

/** Create a singleton table with the given entry */
fun <R, C, V> tableOf(entry: Entry<R, C, V>): Table<R, C, V> = SingletonTable(entry)

/** Create a table with the given entries */
fun <R, C, V> tableOf(vararg entries: Entry<R, C, V>): Table<R, C, V> = entries.asIterable().toTable()

/** Create a mutable table with the given entries */
fun <R, C, V> mutableTableOf(vararg entries: Entry<R, C, V>): MutableTable<R, C, V> = entries.asIterable().toMutableTable()

/** Create a copy of this table */
fun <R, C, V> Table<R, C, V>.toTable(): Table<R, C, V> = entries.toTable()

/** Create a mutable copy of this table */
fun <R, C, V> Table<R, C, V>.toMutableTable(): MutableTable<R, C, V> = entries.toMutableTable()

/** Return true if, and only if, the table contains the given row-column key pair */
operator fun <R, C> Table<R, C, *>.contains(key: Pair<R, C>) = contains(key.first, key.second)

/** Return a map representation of the table */
fun <R, C, V> Table<R, C, V>.toMap(): Map<Pair<R, C>, V> = entries.associate { it.toPair() }

/** Create a sequence that returns all entries of this table */
fun <R, C, V> Table<R, C, V>.asSequence(): Sequence<Entry<R, C, V>> = object : Sequence<Entry<R, C, V>> {
    override fun iterator() = this@asSequence.iterator()
}

/** Create an iterable that returns all entries of this table */
fun <R, C, V> Table<R, C, V>.asIterable(): Iterable<Entry<R, C, V>> = object : Iterable<Entry<R, C, V>> {
    override fun iterator() = this@asIterable.iterator()
}

/** Return a wrapper for this table which synchronize all access to the table making it thread safe */
fun <R, C, V> MutableTable<R, C, V>.asSynchronized(): MutableTable<R, C, V> = SynchronizedTable(this)

/** Return a thread safe copy of the table */
fun <R, C, V> Table<R, C, V>.toSynchronized(): MutableTable<R, C, V> = toMutableTable().asSynchronized()

/** Put the given entry in the table */
operator fun <R, C, V> MutableTable<R, C, V>.plusAssign(entry: Entry<R, C, V>) = put(entry)

/** Put the given entries in the table */
operator fun <R, C, V> MutableTable<R, C, V>.plusAssign(entries: Iterable<Entry<R, C, V>>) = putAll(entries)

/** Remove the given entry key from the table */
operator fun <R, C> MutableTable<R, C, *>.minusAssign(key: Pair<R, C>) {
    remove(key)
}

/** Remove the given entry keys from the table */
operator fun <R, C> MutableTable<R, C, *>.minusAssign(keys: Iterable<Pair<R, C>>) = removeAll(keys)

/**
 * Return all the values of the row mapped by column
 *
 * @see Table.getRow
 */
operator fun <R, C, V> Table<R, C, V>.get(row: R): Map<C, V> = getRow(row)

/** Return a new table with the same entries plus the given new entry */
operator fun <R, C, V> Table<R, C, V>.plus(entry: Entry<R, C, V>): Table<R, C, V> =
        (asSequence() + entry).toTable()

/** Return a new table with the same entries plus the given new entries */
operator fun <R, C, V> Table<R, C, V>.plus(entries: Collection<Entry<R, C, V>>): Table<R, C, V> =
        (asSequence() + entries).toTable()

/** Return a new table with the same entries plus entries of the given table */
operator fun <R, C, V> Table<R, C, V>.plus(table: Table<R, C, V>): Table<R, C, V> =
        (asSequence() + table.asSequence()).toTable()

/** Return a new table with the same entries except the given row */
fun <R, C, V> Table<R, C, V>.minusRow(row: R): Table<R, C, V> =
        if (!containsRow(row)) this
        else asSequence().filterNot { it.row == row }.toTable()

/** Return a new table with the same entries except the given column */
fun <R, C, V> Table<R, C, V>.minusColumn(column: C): Table<R, C, V> =
        if (!containsColumn(column)) this
        else asSequence().filterNot { it.column == column }.toTable()

/** Return a new table with the same entries except the row-column pair */
operator fun <R, C, V> Table<R, C, V>.minus(key: Pair<R, C>): Table<R, C, V> =
        if (key !in this) this
        else asSequence().filterNot { it.row == key.first && it.column == key.second }.toTable()

/** Performs the given [action] on each entry */
inline fun <R, C, V> Table<R, C, V>.forEach(action: (Entry<R, C, V>) -> Unit) =
        asSequence().forEach(action)

/** Returns `true` if all entries match the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.all(predicate: (Entry<R, C, V>) -> Boolean): Boolean =
        asSequence().all(predicate)

/** Returns `true` if map has at least one entry */
fun Table<*, *, *>.any(): Boolean =
        asSequence().any()

/** Returns `true` if at least one entry matches the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.any(predicate: (Entry<R, C, V>) -> Boolean): Boolean =
        asSequence().any(predicate)

/** Returns `true` if the map has no entries */
fun Table<*, *, *>.none(): Boolean =
        asSequence().none()

/** Returns `true` if no entry match the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.none(predicate: (Entry<R, C, V>) -> Boolean): Boolean =
        asSequence().none(predicate)

/** Returns the number of entries in this map */
fun Table<*, *, *>.count(): Int = size

/** Returns the number of entries matching the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.count(predicate: (Entry<R, C, V>) -> Boolean): Int =
        asSequence().count(predicate)

/** Returns the first entry yielding the largest value of the given function or `null` if there are no entries */
inline fun <R, C, V, T : Comparable<T>> Table<R, C, V>.maxBy(selector: (Entry<R, C, V>) -> T): Entry<R, C, V>? =
        asSequence().maxBy(selector)

/** Returns the first entry having the largest value according to the provided [comparator] or `null` if there are no entries */
fun <R, C, V> Table<R, C, V>.maxWith(comparator: Comparator<Entry<R, C, V>>): Entry<R, C, V>? =
        asSequence().maxWith(comparator)

/** Returns the first entry yielding the smallest value of the given function or `null` if there are no entries */
inline fun <R, C, V, T : Comparable<T>> Table<R, C, V>.minBy(selector: (Entry<R, C, V>) -> T): Entry<R, C, V>? =
        asSequence().minBy(selector)

/** Returns the first entry having the smallest value according to the provided [comparator] or `null` if there are no entries */
fun <R, C, V> Table<R, C, V>.minWith(comparator: Comparator<Entry<R, C, V>>): Entry<R, C, V>? =
        asSequence().minWith(comparator)

/** Returns a new table containing all entries matching the given [predicate] */
fun <R, C, V> Table<R, C, V>.filter(predicate: (Entry<R, C, V>) -> Boolean): Table<R, C, V> =
        asSequence().filter(predicate).toTable()

/** Returns a new table containing all entries not matching the given [predicate] */
fun <R, C, V> Table<R, C, V>.filterNot(predicate: (Entry<R, C, V>) -> Boolean): Table<R, C, V> =
        asSequence().filterNot(predicate).toTable()

/** Returns a new table containing all entries on rows matching the given [predicate] */
fun <R, C, V> Table<R, C, V>.filterRows(predicate: (R) -> Boolean): Table<R, C, V> =
        asSequence().filter { predicate(it.row) }.toTable()

/** Returns a new table containing all entries on columns matching the given [predicate] */
fun <R, C, V> Table<R, C, V>.filterColumns(predicate: (C) -> Boolean): Table<R, C, V> =
        asSequence().filter { predicate(it.column) }.toTable()

/** Returns a new table containing all entries with a values matching the given [predicate] */
fun <R, C, V> Table<R, C, V>.filterValues(predicate: (V) -> Boolean): Table<R, C, V> =
        asSequence().filter { predicate(it.value) }.toTable()

/** Returns a list containing the results of applying the given [transform] function to each entry in the original table */
fun <R, C, V, T> Table<R, C, V>.map(transform: (Entry<R, C, V>) -> T): Collection<T> =
        asSequence().map(transform).toList()

/** Returns a list containing the results of applying the given [transform] function to each entry in the original table */
fun <R, C, V, T> Table<R, C, V>.flatMap(transform: (Entry<R, C, V>) -> Iterable<T>): Collection<T> =
        asIterable().flatMap(transform)

/** Returns a list containing only the non-null results of applying the given [transform] function to each entry in the original table */
fun <R, C, V, T : Any> Table<R, C, V>.mapNotNull(transform: (Entry<R, C, V>) -> T?): Collection<T> =
        asSequence().mapNotNull(transform).toList()

/** Returns a new table with entries having the rows obtained by applying the [transform] function to each entry */
fun <R, C, V, T> Table<R, C, V>.mapRows(transform: (Entry<R, C, V>) -> T): Table<T, C, V> =
        asSequence().map { entry(transform(it), it.column, it.value) }.toTable()

/** Returns a new table with entries having the columns obtained by applying the [transform] function to each entry */
fun <R, C, V, T> Table<R, C, V>.mapColumns(transform: (Entry<R, C, V>) -> T): Table<R, T, V> =
        asSequence().map { entry(it.row, transform(it), it.value) }.toTable()

/** Returns a new table with entries having the values obtained by applying the [transform] function to each entry */
fun <R, C, V, T> Table<R, C, V>.mapValues(transform: (Entry<R, C, V>) -> T): Table<R, C, T> =
        asSequence().map { entry(it.row, it.column, transform(it)) }.toTable()

