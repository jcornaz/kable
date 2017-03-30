package kable

class SingletonTable<R, C, V>(val entry: Table.Entry<R, C, V>) : Table<R, C, V> {
    override val size: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val rows: Set<R>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val columns: Set<C>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val values: Collection<V>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val entries: Set<Table.Entry<R, C, V>>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun getRow(row: R): Map<C, V> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getColumn(column: C): Map<R, V> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(row: R, column: C): V? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}