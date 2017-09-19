package kable

class BiKeyMapTest : TableTest() {
    override fun <R, C, V> createTable(vararg entries: Table.Entry<R, C, V>) = BiKeyMap(entries.toList())
}
