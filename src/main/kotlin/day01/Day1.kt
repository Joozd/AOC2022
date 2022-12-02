package day01

import common.Solution
import common.extensions.toInts

/*
 * Fastest solution given, a much more readable one that is just over 10 times slower also given.
 * Times checked by running the function 10K times.
 */
class Day1: Solution(1) {
    //caching elves because the same value is used for both answers
    private var _elves: List<Int>? = null
    private fun elves(input: List<String>) = _elves ?: getBiggestThreeElves(input).also { _elves = it}

        //getBiggestThreeElves()  runs in about 0.028 ms/run
        //getBiggestThreeElvesReadable()  runs in about 0.36 ms/run

    // input is ignored, no unit tests for this
    override fun answer1(input: List<String>) = elves(input).last()
    override fun answer2(input: List<String>) = elves(input).sum()


    /**
     * Alternative answer, easier to read and understand but slower by a factor 10+
     */
    @Suppress("unused")
    private fun getBiggestThreeElvesReadable(input: List<String>): List<Int> =
        inputAsOne(input).split("\n\n")    // split input per elf
            .map {
                it.lines().toInts()         // change elves' string to ints
                    .sum()                      // just total weight
            }
            .sorted().takeLast(3)         // take 3 biggest sums


    /**
     * Optimized answer, only calculate top 3 elves. Only touch each line once.
     */
    private fun getBiggestThreeElves(inputLines: List<String>): List<Int> {
        val elves = mutableListOf(0,0,0) // MutableList is faster than IntArray (not sure why, compiler optimized?)
        var currentElf = 0

        // Add inputs to current elf as long as line is not empty.
        // If line is empty, add elf to biggest 3 if it is big enough.
        for(c in inputLines){
            if (c.isEmpty()){
                elves.insertIfBigEnough(currentElf)
                currentElf = 0
            }
            else currentElf += c.toInt()
        }
        //last line in input is not empty so add current elf if big enough
        elves.insertIfBigEnough(currentElf)

        return elves
    }


    // Assumes a sorted MutableList of size 3.
    // Inserts [v] if it is bigger than smallest value, keeps list sorted.
    // This boilerplate is marginally faster than just replacing first value and then sorting.
    private fun MutableList<Int>.insertIfBigEnough(v: Int){
        when{
            v >= this[2] -> {
                this[0] = this[1]
                this[1] = this[2]
                this[2] = v
            }
            v >= this[1] -> {
                this[0] = this[1]
                this[1] = v
            }
            v > this[0] -> {
                this[0] = v
            }
        }
    }
}