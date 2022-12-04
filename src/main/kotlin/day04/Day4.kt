package day04

import common.Solution
import common.extensions.grabInts

/**
 * Initially I did this the clean, understandable way
 * (building IntRanges, checking if they overlap with some  simple extension functions).
 * Then, I went for the "performant" way, grabbing ints and comparing them.
 * This was about 3 times faster, I did not expect such a big difference
 * Turn out, the "String.grabInts()" function I made (@see extensions.StringExt.kt) is just a lot faster than
 * <pre>
 *  line.split(',').map { elf ->
 *      elf.split('-').let {
 *          it.first().toInt()..it.last().toInt()
 *      }
 *  }
 * </pre>
 * With this fixed, the "faster" functions are about 10% faster.
 * The price of this is much less readability and a huge increase in possible human error.
 */
class Day4: Solution(4) {
    override fun answer1(input: List<String>) = input.count { oneIsInTheOtherFaster(it) }

    override fun answer2(input: List<String>) = input.count { isOverlappingFaster(it) }

    // runs in about 0.057ms
    private fun oneIsInTheOtherFaster(line: String) =
        line.grabInts().let { numbers ->
                numbers[LEFT_START] <= numbers[RIGHT_START] && numbers[RIGHT_END] <= numbers[LEFT_END]
            ||  numbers[RIGHT_START] <= numbers[LEFT_START] && numbers[LEFT_END] <= numbers[RIGHT_END]
        }

    // runs in about 0.057ms
    private fun isOverlappingFaster(line: String) =
        line.grabInts().let { numbers ->
            numbers[LEFT_START] <= numbers[RIGHT_END] && numbers[RIGHT_START] <= numbers[LEFT_END]
        }

    //runs in about 0.062-0.065 ms
    @Suppress("unused")
    private fun oneIsInTheOther(line: String): Boolean{
        val assignment: List<IntRange> = assignmentsAsIntRanges(line)
        return assignment[0] in assignment[1] || assignment[1] in assignment[0]
    }

    //runs in about 0.061-0.065 ms
    @Suppress("unused")
    private fun isOverlapping(line: String): Boolean{
        val assignment: List<IntRange> = assignmentsAsIntRanges(line)
        return assignment[0] overlaps assignment[1]
    }

    private fun assignmentsAsIntRanges(line: String): List<IntRange> =
        line.grabInts().let{
            listOf(it[LEFT_START]..it[LEFT_END], it[RIGHT_START]..it[RIGHT_END])
        }

    private operator fun IntRange.contains(other: IntRange): Boolean =
        first <= other.first && last >= other.last

    private infix fun IntRange.overlaps(other: IntRange): Boolean =
        other.first in this || other.last in this || this in other

    companion object{
        private const val LEFT_START = 0
        private const val LEFT_END = 1
        private const val RIGHT_START = 2
        private const val RIGHT_END = 3
    }
}