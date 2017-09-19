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

@file:JvmName("Collections")

package com.github.jcornaz.kable.util

import com.github.jcornaz.kable.MutableTable
import com.github.jcornaz.kable.Table
import com.github.jcornaz.kable.Table.Entry
import com.github.jcornaz.kable.impl.BiKeyMap
import com.github.jcornaz.kable.impl.SingletonTable
import com.github.jcornaz.kable.impl.ThreeMapTable

/** Create a new table with this entries */
fun <R, C, V> Iterable<Entry<R, C, V>>.toTable(): Table<R, C, V> {

    val i = iterator()
    if (!i.hasNext()) return emptyTable()

    val elt = i.next()
    if (!i.hasNext()) return SingletonTable(elt)

    return BiKeyMap(this)
}

/** Create a new table with this entries */
fun <R, C, V> Sequence<Entry<R, C, V>>.toTable(): Table<R, C, V> = asIterable().toTable()

/** Create a new mutable table with this entries */
fun <R, C, V> Iterable<Entry<R, C, V>>.toMutableTable(): MutableTable<R, C, V> = ThreeMapTable(this)

/** Create a new mutable table with this entries */
fun <R, C, V> Sequence<Entry<R, C, V>>.toMutableTable(): MutableTable<R, C, V> = asIterable().toMutableTable()

/**
 * Groups elements of the original collection by the key returned by the given [keySelector] function
 * applied to each element and returns a [Table] where each group key is associated with a list of corresponding elements.
 *
 * The returned table preserves the entry iteration order of the keys produced from the original collection.
 */
inline fun <R, C, T> Iterable<T>.groupTableBy(keySelector: (T) -> Pair<R, C>): Table<R, C, List<T>> =
        asSequence().groupBy(keySelector).toTable()

/**
 * Groups values returned by the [valueTransform] function applied to each element of the original collection
 * by the key returned by the given [keySelector] function applied to the element
 * and returns a [Table] where each group key is associated with a list of corresponding values.
 *
 * The returned table preserves the entry iteration order of the keys produced from the original collection.
 */
inline fun <R, C, V, T> Iterable<T>.groupTableBy(keySelector: (T) -> Pair<R, C>, valueTransform: (T) -> V): Table<R, C, List<V>> =
        asSequence().groupBy(keySelector, valueTransform).toTable()

/**
 * Returns a [Table] containing key-value pairs provided by [transform] function
 * applied to elements of the given collection.
 *
 * If any of two pairs would have the same key the last one gets added to the table.
 *
 * The returned table preserves the entry iteration order of the original collection.
 */
fun <R, C, V, T> Iterable<T>.associateTable(transform: (T) -> Entry<R, C, V>): Table<R, C, V> =
        asSequence().map(transform).toTable()

/**
 * Returns a [Table] containing the elements from the given collection indexed by the key
 * returned from [keySelector] function applied to each element.
 *
 * If any two elements would have the same key returned by [keySelector] the last one gets added to the table.
 */
inline fun <R, C, V> Iterable<V>.associateTableBy(keySelector: (V) -> Pair<R, C>): Table<R, C, V> =
        asSequence().associateBy(keySelector).toTable()

/**
 * Returns a [Table] containing the values provided by [valueTransform] and indexed by [keySelector] functions applied to elements of the given collection.
 *
 * If any two elements would have the same key returned by [keySelector] the last one gets added to the table.
 */
inline fun <R, C, V, T> Iterable<T>.associateTableBy(keySelector: (T) -> Pair<R, C>, valueTransform: (T) -> V): Table<R, C, V> =
        asSequence().associateBy(keySelector, valueTransform).toTable()
