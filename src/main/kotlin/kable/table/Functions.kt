package kable.table

fun <R, C, V> Triple<R, C, V>.toTableEntry() = tableEntry(first, second, third)
fun <R, C> Pair<R, C>.toTableKey() = tableKey(first, second)

fun <R, C, V> tableEntry(row: R, column: C, value: V): Table.Entry<R, C, V> = SimpleTableEntry(row, column, value)
fun <R, C> tableKey(row: R, column: C): Table.Key<R, C> = SimpleTableKey(row, column)

fun <R, C, V> emptyTable(): Table<R, C, V> = BiKeyMap()