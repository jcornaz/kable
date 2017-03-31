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