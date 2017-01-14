package kable

interface TableFactory {
    fun <R, C, V> create(entries: Collection<Table.Entry<R, C, V>>): Table<R, C, V>
}