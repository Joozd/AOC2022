package day04

import common.Solution

/*
 * Some optimization might come from not making IntRanges but I think they really helps with readability.
 * Just making 4 ints will probably be the fastest, might implement that later.
 * Current runtimes: About 0.16ms-0.18ms / answer for both answers
 */
class Day4: Solution(4) {
    override fun answer1(input: List<String>) = input.count { oneIsInTheOther(it) }

    override fun answer2(input: List<String>) = input.count { isOverlapping(it) }

    //runs in about 0.16-0.18 ms
    private fun oneIsInTheOther(line: String): Boolean{
        val pair: List<IntRange> = pairAsIntRanges(line)
        return pair[0] in pair[1] || pair[1] in pair[0]
    }

    //runs in about 0.16-0.18 ms
    private fun isOverlapping(line: String): Boolean{
        val pair: List<IntRange> = pairAsIntRanges(line)
        return pair[0] overlaps pair[1]
    }

    private fun pairAsIntRanges(line: String): List<IntRange> = line.split(',').map { elf ->
        elf.split('-').let {
            it.first().toInt()..it.last().toInt()
        }
    }

    private operator fun IntRange.contains(other: IntRange): Boolean =
        first <= other.first && last >= other.last

    private infix fun IntRange.overlaps(other: IntRange): Boolean =
        other.first in this || other.last in this || this in other
}