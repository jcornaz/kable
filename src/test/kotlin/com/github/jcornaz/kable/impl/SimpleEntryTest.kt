package com.github.jcornaz.kable.impl

import com.github.jcornaz.kable.util.entry
import kotlin.test.*

class SimpleEntryTest {

    @Test fun testEquals() {
        assertEquals(entry('A', 1, "test"), entry('A', 1, "test"))

        assertNotEquals(entry('B', 1, "test"), entry('A', 1, "test"))
        assertNotEquals(entry('A', 2, "test"), entry('A', 1, "test"))
        assertNotEquals(entry('A', 1, "tested"), entry('A', 1, "test"))
    }
}
