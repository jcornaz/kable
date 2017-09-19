package com.github.jcornaz.kable.impl

import com.github.jcornaz.kable.Table
import com.github.jcornaz.kable.util.TableTest

class BiKeyMapTest : TableTest() {
    override fun <R, C, V> createTable(vararg entries: Table.Entry<R, C, V>) = BiKeyMap(entries.toList())
}
