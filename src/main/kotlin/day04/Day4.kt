package day04

import common.Solution

/*
 * Some optimization might come from not making IntRanges but I think they really help with readability.
 * Not even sure about that, as it needs SOME collection, maybe a pair is faster?
 * A pair will need extra code though and IMHO pairs are evil and make everything way too unreadable.
 * Biggest gains would be not jumping functions but just doing it all in one line.
 */
class Day4: Solution(4) {
    override fun answer1(input: List<String>) = input.count { oneIsInTheOther(it) }

    override fun answer2(input: List<String>) = input.count { isOverlapping(it) }

    //runs in about 0.18 ms
    private fun oneIsInTheOther(line: String): Boolean{
        val pair: List<IntRange> = pairAsIntRanges(line)
        return pair[0] in pair[1] || pair[1] in pair[0]
    }

    //runs in about 0.18 ms
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