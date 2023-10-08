package common

/**
 * Represents a window into a 2D character array.
 * Example:
 *  val window = CharArrayWindow2D(charArray2D, 1..2, 1..2)
 *  println(window[1][1])  // Accessing
 *  window[1][1] = 'z'     // Modifying
 *
 */
class CharArrayWindow2D(
    private val charArray2d: Array<CharArray>,
    val rowRange: IntRange,
    val columnRange: IntRange
): Iterable<CharArrayWindow2D.WindowedCharArray> {
    private val allowedRow = 0..(rowRange.last - rowRange.first)
    private val allowedColumn = 0..(columnRange.last - columnRange.first)

    /**
     * @return a [WindowedCharArray] for the specified row, ensuring that the row index is within the allowed range.
     */
    operator fun get(row: Int): WindowedCharArray {
        require(row in allowedRow)
        return WindowedCharArray(charArray2d[row + rowRange.first])
    }

    /**
     * Represents a window into a 1D character array (a row of the original 2D array).
     * This uses the values in the surrounding class for the window.
     */
    inner class WindowedCharArray(private val charArray: CharArray): Iterable<Char>{
        operator fun get(j: Int): Char{
            require(j in allowedColumn)
            return charArray[j + columnRange.first]
        }

        /**
         * Sets the character at the specified column, ensuring that the column index is within the allowed range.
         */
        operator fun set(j: Int, value: Char){
            require(j in allowedColumn)
            charArray[j + columnRange.first] = value
        }

        override fun iterator() = object: Iterator<Char>{
            var currentIndex = 0
            override fun hasNext(): Boolean =
                currentIndex <= columnRange.last - columnRange.first

            override fun next(): Char =
                this@WindowedCharArray[currentIndex++]
        }

        override fun toString() = this.toList().joinToString("")
    }

    override fun iterator() = object: Iterator<WindowedCharArray>{
        var currentIndex = 0
        override fun hasNext(): Boolean =
            currentIndex <= rowRange.last - rowRange.first


        override fun next(): WindowedCharArray =
            this@CharArrayWindow2D[currentIndex++]

    }

    override fun toString() = this.toList().joinToString("\n")
}