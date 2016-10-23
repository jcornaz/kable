package kable.table

data class SimpleTableKey<out R, out C>(override val row: R, override val column: C) : Table.Key<R, C>