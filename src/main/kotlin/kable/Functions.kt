package kable

fun <R, C, V> emptyTable(): Table<R, C, V> = BiKeyMap()

fun <R, C, V> entry(row: R, column: C, value: V): Table.Entry<R, C, V> = SimpleTableEntry(row, column, value)

fun <R, C, V> tableOf(entries: Collection<Table.Entry<R, C, V>>) = BiKeyMap(entries)

fun <R, C, V> tableOf(vararg entries: Table.Entry<R, C, V>) = tableOf(entries.toList())