@file:JvmName("Maps")

package kable

/** Return a [Table] corresponding to the map where keys will be split into rows ans columns */
fun <R, C, V> Map<Pair<R, C>, V>.toTable(): Table<R, C, V> = when {
    isEmpty() -> emptyTable()
    size == 1 -> entries.first().let { (key, value) -> entry(key.first, key.second, value) }.let { tableOf(it) }
    else -> BiKeyMap(this)
}