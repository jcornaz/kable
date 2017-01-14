package kable

/**
 * Implementation of a [Table] with
 */
data class BiKeyMap<R, C, V>(val map: Map<Pair<R, C>, V> = emptyMap()) : Table<R, C, V> {

    override val size by lazy { map.size }

    /** Map of maps by rows, then by columns */
    val rowsMap by lazy {
        map.entries.groupBy { it.key.first }.mapValues { entry ->
            entry.value.associate {
                it.key.second to it.value
            }
        }
    }

    /** Map of maps by columns, then by rows */
    val columnsMap by lazy {
        map.entries.groupBy { it.key.second }.mapValues { entry ->
            entry.value.associate {
                it.key.first to it.value
            }
        }
    }

    override val rows by lazy { rowsMap.keys }
    override val columns by lazy { columnsMap.keys }
    override val values by lazy { map.values }

    override val entries by lazy { map.map { entry(it.key.first, it.key.second, it.value) }.toSet() }

    constructor(entries: Collection<Table.Entry<R, C, V>>) : this(
            entries.associate { (it.row to it.column) to it.value }
    )

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun contains(row: R, column: C) = (row to column) in map

    override fun getRow(row: R) = rowsMap[row] ?: emptyMap()
    override fun getColumn(column: C) = columnsMap[column] ?: emptyMap()

    override fun get(row: R, column: C) = get(row to column)
    operator fun get(key: Pair<R, C>) = map[key]

    override fun iterator() = object : Iterator<Table.Entry<R, C, V>> {
        val i = map.iterator()

        override fun hasNext() = i.hasNext()
        override fun next() = i.next().let { entry(it.key.first, it.key.second, it.value) }
    }
}