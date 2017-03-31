package kable

import kable.Table.Entry

/** Create a new table with this entries */
fun <R, C, V> Collection<Entry<R, C, V>>.toTable(): Table<R, C, V> = tableOf(this)