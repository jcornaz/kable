package kable.table

operator fun <K, V> Map<K, V>.minus(key: K): Map<K, V> =
        if (key !in this) this else filterKeys { it != key }