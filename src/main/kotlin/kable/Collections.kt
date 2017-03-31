package kable

import kable.Table.Entry

/** Create a new table with this entries */
fun <R, C, V> Collection<Entry<R, C, V>>.toTable(): Table<R, C, V> = tableOf(this)

inline fun <R, C, T> Collection<T>.groupTableBy(keySelector: (T) -> Pair<R, C>): Table<R, C, List<T>> =
        groupBy(keySelector).toTable()

inline fun <R, C, V, T> Collection<T>.groupTableBy(keySelector: (T) -> Pair<R, C>, valueTransform: (T) -> V): Table<R, C, List<V>> =
        groupBy(keySelector, valueTransform).toTable()