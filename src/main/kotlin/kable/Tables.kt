@file:JvmName("Tables")

package kable

fun <R, C, V> emptyTable(): Table<R, C, V> = BiKeyMap()

fun <R, C, V> entry(row: R, column: C, value: V): Table.Entry<R, C, V> = SimpleTableEntry(row, column, value)

fun <R, C, V> tableOf(entries: Collection<Table.Entry<R, C, V>>) = BiKeyMap(entries)

fun <R, C, V> tableOf(vararg entries: Table.Entry<R, C, V>) = tableOf(entries.toList())

operator fun <R, C, V> Table<R, C, V>.plus(entry: Table.Entry<R, C, V>): Table<R, C, V> =
        factory.create(entries + entry)

operator fun <R, C, V> Table<R, C, V>.plus(entries: Collection<Table.Entry<R, C, V>>): Table<R, C, V> =
        factory.create(this.entries + entries)

operator fun <R, C, V> Table<R, C, V>.plus(table: Table<R, C, V>): Table<R, C, V> =
        factory.create(entries + table.entries)

fun <R, C, V> Table<R, C, V>.minusRow(row: R): Table<R, C, V> =
        factory.create(entries.filterNot { it.row == row })

fun <R, C, V> Table<R, C, V>.minusColumn(column: C): Table<R, C, V> =
        factory.create(entries.filterNot { it.column == column })

operator fun <R, C, V> Table<R, C, V>.minus(key: Pair<R, C>): Table<R, C, V> =
        factory.create(entries.filterNot { it.row == key.first && it.column == key.second })