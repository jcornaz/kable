package kable

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