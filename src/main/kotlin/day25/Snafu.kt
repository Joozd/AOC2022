package day25

/**
 * All operations are in reverse order, so string is saved reversed internally
 * This can add SNAFU's without converting to another type!
 */
class Snafu private constructor(private val stringValue: String) {
    operator fun plus(other: Snafu): Snafu{
        var currentDigit = 0
        var remainder = 0
        val result = StringBuilder()
        val longestNumber = maxOf(stringValue.length, other.stringValue.length)
        while(currentDigit <= longestNumber){
            var added = getDigitValue(currentDigit) + other.getDigitValue(currentDigit) + remainder
            when{
                added > 2 -> {
                    remainder = 1
                    added -= 5
                }
                added < -2 -> {
                    remainder = -1
                    added += 5
                }
                else -> remainder = 0
            }
            result.append(reverseSnafuTable[added]!!)
            currentDigit++
        }
        if(remainder != 0){
            result.append(reverseSnafuTable[remainder]!!)
        }
        // remove leading zero's that pop up
        while(result.last() == '0')
            result.setLength(result.length -1)

        return Snafu(result.toString())
    }

    private fun getDigitValue(location: Int): Int =
        snafuTable[stringValue.getOrElse(location) { '0' }]!!

    override fun toString() = stringValue.reversed()

    private val snafuTable = buildMap{
        put('=', -2)
        put('-', -1)
        put('0', 0)
        put('1', 1)
        put('2', 2)
    }

    private val reverseSnafuTable = snafuTable.entries.associate {
        it.value to it.key
    }

    companion object{
        fun ofString(string: String): Snafu {
            require(string.all { it in "=-012"}) { "Bad SNAFU number: $string"}
            return Snafu(string.reversed())
        }
    }
}
