package com.github.jcornaz.kable.impl

import com.github.jcornaz.kable.Table
import com.github.jcornaz.kable.util.TableTest
import com.github.jcornaz.kable.util.entry
import com.github.jcornaz.kable.util.tableOf
import org.junit.Assert
import org.junit.Test

class ThreeMapTableTest : MutableTableTest() {
    override fun <R, C, V> createTable(vararg entries: Table.Entry<R, C, V>) = ThreeMapTable(entries.toList())
}
