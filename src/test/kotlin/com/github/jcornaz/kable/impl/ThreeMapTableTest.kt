package com.github.jcornaz.kable.impl

import com.github.jcornaz.kable.Table

class ThreeMapTableTest : MutableTableTest() {
    override fun <R, C, V> createTable(vararg entries: Table.Entry<R, C, V>) = ThreeMapTable(entries.toList())
}
