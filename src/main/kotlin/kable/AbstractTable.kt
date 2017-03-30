package kable

/** Abstraction of a table */
abstract class AbstractTable<R, C, V> : Table<R, C, V> {
    override fun equals(other: Any?) = other is Table<*, *, *> && other.entries == entries
    override fun hashCode() = entries.hashCode()
}