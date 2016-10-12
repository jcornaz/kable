package kable.encoding

/**
 * Encoding base
 */
interface Base {

    /**
     * Number of symbol of this base
     */
    val symbolCount: Int

    /**
     * Get the symbol corresponding to a value
     *
     * @param value Value of the desired symbol
     * @throws IllegalArgumentException If the base doesn't have a single symbol to represent the givent value
     * @see contains
     */
    @Throws(IllegalArgumentException::class)
    operator fun get(value: Byte): Char

    /**
     * Get the value corresponding to a symbol
     *
     * @param char Symbol that represent the desired value
     * @throws IllegalArgumentException If the base doesn't contains the given symbol
     * @see contains
     */
    @Throws(IllegalArgumentException::class)
    operator fun get(char: Char): Byte

    /**
     * Check if the base have a single symbol for the given value
     *
     * @param value Value to check
     * @return True if, and only if, this base has a symbol associated toe the given value
     */
    operator fun contains(value: Byte): Boolean =
            value >= 0 && value < symbolCount

    /**
     * Check if the base have contains a given symbol
     *
     * @param char Character to check
     * @return True if, and only if, this base has a value associated to the given symbol
     */
    operator fun contains(char: Char): Boolean
}