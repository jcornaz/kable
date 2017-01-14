package kable

/** Simple table entry implementation*/
data class SimpleTableEntry<out R, out C, out V>(
        override val row: R,
        override val column: C,
        override val value: V
) : Table.Entry<R, C, V>