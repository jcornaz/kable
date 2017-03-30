@file:JvmName("Tables")

package kable

import java.util.*

/** Create an empty table */
fun <R, C, V> emptyTable(): Table<R, C, V> = BiKeyMap()

/** Create a table entry with the given row, column and value */
fun <R, C, V> entry(row: R, column: C, value: V): Table.Entry<R, C, V> = SimpleTableEntry(row, column, value)

/** Create a table with the given entries */
fun <R, C, V> tableOf(entries: Collection<Table.Entry<R, C, V>>): Table<R, C, V> =
        if (entries.size == 1) tableOf(entries.first()) else BiKeyMap(entries)

/** Create a singleton table with the given entry */
fun <R, C, V> tableOf(entry: Table.Entry<R, C, V>): Table<R, C, V> = SingletonTable(entry)

/** Create a table with the given entries */
fun <R, C, V> tableOf(vararg entries: Table.Entry<R, C, V>): Table<R, C, V> = tableOf(entries.toList())

/** Return true if, and only if, the table contains the given row-column key pair */
operator fun <R, C> Table<R, C, *>.contains(key: Pair<R, C>) = contains(key.first, key.second)

/** Return a new table with the same entries plus the given new entry */
operator fun <R, C, V> Table<R, C, V>.plus(entry: Table.Entry<R, C, V>): Table<R, C, V> =
        BiKeyMap(entries + entry)

/** Return a new table with the same entries plus the given new entries */
operator fun <R, C, V> Table<R, C, V>.plus(entries: Collection<Table.Entry<R, C, V>>): Table<R, C, V> =
        entries.fold(this) { table, entry -> table + entry }

/** Return a new table with the same entries plus entries of the given table */
operator fun <R, C, V> Table<R, C, V>.plus(table: Table<R, C, V>): Table<R, C, V> =
        this + table.entries

/** Return a new table with the same entries except the given row */
fun <R, C, V> Table<R, C, V>.minusRow(row: R): Table<R, C, V> =
        if (!containsRow(row)) this else BiKeyMap(entries.filterNot { it.row == row })

/** Return a new table with the same entries except the given column */
fun <R, C, V> Table<R, C, V>.minusColumn(column: C): Table<R, C, V> =
        if (!containsColumn(column)) this else BiKeyMap(entries.filterNot { it.column == column })

/** Return a new table with the same entries except the row-column pair */
operator fun <R, C, V> Table<R, C, V>.minus(key: Pair<R, C>): Table<R, C, V> =
        if (key !in this) this else BiKeyMap(entries.filterNot { it.row == key.first && it.column == key.second })

/** Performs the given [action] on each entry */
inline fun <R, C, V> Table<R, C, V>.forEach(action: (Table.Entry<R, C, V>) -> Unit): Unit =
        entries.forEach(action)

/** Returns `true` if all entries match the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.all(predicate: (Table.Entry<R, C, V>) -> Boolean): Boolean =
        entries.all(predicate)

/** Returns `true` if map has at least one entry */
fun Table<*, *, *>.any(): Boolean = entries.any()

/** Returns `true` if at least one entry matches the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.any(predicate: (Table.Entry<R, C, V>) -> Boolean): Boolean =
        entries.any(predicate)

/** Returns `true` if the map has no entries */
fun Table<*, *, *>.none(): Boolean = entries.none()

/** Returns `true` if no entry match the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.none(predicate: (Table.Entry<R, C, V>) -> Boolean): Boolean =
        entries.none(predicate)

/** Returns the number of entries in this map */
fun Table<*, *, *>.count(): Int = entries.count()

/** Returns the number of entries matching the given [predicate] */
inline fun <R, C, V> Table<R, C, V>.count(predicate: (Table.Entry<R, C, V>) -> Boolean): Int =
        entries.count(predicate)

/** Returns the first entry yielding the largest value of the given function or `null` if there are no entries */
inline fun <R, C, V, T : Comparable<T>> Table<R, C, V>.maxBy(selector: (Table.Entry<R, C, V>) -> T): Table.Entry<R, C, V>? =
        entries.maxBy(selector)

/** Returns the first entry having the largest value according to the provided [comparator] or `null` if there are no entries */
fun <R, C, V> Table<R, C, V>.maxWith(comparator: Comparator<Table.Entry<R, C, V>>): Table.Entry<R, C, V>? =
        entries.maxWith(comparator)

/** Returns the first entry yielding the smallest value of the given function or `null` if there are no entries */
inline fun <R, C, V, T : Comparable<T>> Table<R, C, V>.minBy(selector: (Table.Entry<R, C, V>) -> T): Table.Entry<R, C, V>? =
        entries.minBy(selector)

/** Returns the first entry having the smallest value according to the provided [comparator] or `null` if there are no entries */
fun <R, C, V> Table<R, C, V>.minWith(comparator: Comparator<Table.Entry<R, C, V>>): Table.Entry<R, C, V>? =
        entries.minWith(comparator)
