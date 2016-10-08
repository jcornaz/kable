package kable

import kable.MutableTable.MutableEntry

data class SimpleEntry<out R, out C, V>(
        override val row: R,
        override val column: C,
        override var value: V
) : MutableEntry<R, C, V>