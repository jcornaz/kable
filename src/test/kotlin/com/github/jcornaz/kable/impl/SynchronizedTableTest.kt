package com.github.jcornaz.kable.impl

import com.github.jcornaz.kable.Table
import com.github.jcornaz.kable.util.mutableTableOf

class SynchronizedTableTest : MutableTableTest() {
    override fun <R, C, V> createTable(vararg entries: Table.Entry<R, C, V>) = SynchronizedTable(mutableTableOf(*entries))
}

