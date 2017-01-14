@file:JvmName("Tables")

package kable

/** Create an empty table */
fun <R, C, V> emptyTable(): Table<R, C, V> = BiKeyMap()

/** Create a table entry with the given row, column and value */
fun <R, C, V> entry(row: R, column: C, value: V): Table.Entry<R, C, V> = SimpleTableEntry(row, column, value)

/** Create a table with the given entries */
fun <R, C, V> tableOf(entries: Collection<Table.Entry<R, C, V>>) = BiKeyMap(entries)

/** Create a table with the given entries */
fun <R, C, V> tableOf(vararg entries: Table.Entry<R, C, V>) = tableOf(entries.toList())

/** Return a new table with the same entries plus the given new entry */
operator fun <R, C, V> Table<R, C, V>.plus(entry: Table.Entry<R, C, V>): Table<R, C, V> =
        factory.create(entries + entry)

/** Return a new table with the same entries plus the given new entries */
operator fun <R, C, V> Table<R, C, V>.plus(entries: Collection<Table.Entry<R, C, V>>): Table<R, C, V> =
        factory.create(this.entries + entries)

/** Return a new table with the same entries plus entries of the given table */
operator fun <R, C, V> Table<R, C, V>.plus(table: Table<R, C, V>): Table<R, C, V> =
        factory.create(entries + table.entries)

/** Return a new table with the same entries except the given row */
fun <R, C, V> Table<R, C, V>.minusRow(row: R): Table<R, C, V> =
        factory.create(entries.filterNot { it.row == row })

/** Return a new table with the same entries except the given column */
fun <R, C, V> Table<R, C, V>.minusColumn(column: C): Table<R, C, V> =
        factory.create(entries.filterNot { it.column == column })

/** Return a new table with the same entries except the row-column pair */
operator fun <R, C, V> Table<R, C, V>.minus(key: Pair<R, C>): Table<R, C, V> =
        factory.create(entries.filterNot { it.row == key.first && it.column == key.second })