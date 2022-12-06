package day06

import common.Solution

/**
 * Initial solution: for every char from size to end, make a list of last n characters and check if distinct.(0.3 and 1.5 ms)
 * First improvement: for every char from size to end, check if all n characters can be added to a set. (0.10 and 0.59 ms)
 * Second improvement: Stop adding to the set and return as soon as a duplicate is found (0.075 and 0.24 ms)
 * Final improvement: Look for the last repeated character in the substring and jump forward until after that. (0.042 and 0.031ms)
 * Final is actually faster because we can skip bigger sections at once.
 */
class Day6: Solution(6) {
    override fun answer1(input: List<String>) = findMarker(input.first(), 4)

    override fun answer2(input: List<String>) = findMarker(input.first(), 14)

    //One line function for reference. This runs in around 0.10ms / 0.60ms, so about 2.5x and 20x slower.
    @Suppress("unused")
    private fun oneLine(line: String, length: Int): Int =
        (length-1 until line.length).first{ end ->
            line.substring(end-length+1..end).toSet().size == length
        } + 1

    private fun findMarker(line: String, length: Int): Int{
        var i = length // starts at 1, so length 4 is 1,2,3,4
        while(i < line.length){
            val increase = getFirstInstanceOfLastRepeatingCharacter(line, i-length, i)
            if (increase == 0) return i
            i += increase
        }
        return -1
    }

    // Gets the index+1 of the last letter that gets repeated, so in abccd
    // it returns 3 because the third letter (the first c) gets repeated.
    private fun getFirstInstanceOfLastRepeatingCharacter(line: String, start: Int, end: Int): Int{
        val s = HashSet<Char>()
        (end-1).downTo(start-1).forEach{ i ->
            if (!s.add(line[i])) return (i - start + 1)
        }
        return 0
    }
}