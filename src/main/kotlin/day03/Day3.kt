package day03

import common.Solution

class Day3: Solution(3) {
    private val priorities by lazy{
        HashMap<Char, Int>(52).apply{
            ('a'..'z').forEachIndexed { index, c -> this[c] = index + 1 }
            ('A'..'Z').forEachIndexed { index, c -> this[c] = index + 27}
        }
    }

    // sum of values of items in both first and second halves of a string
    override fun answer1(input: List<String>) =
        input.sumOf { rucksack ->
            rucksack.chunked(rucksack.length / 2).let{ compartments ->
                // get first intersecting item
                (compartments[0].toSet() intersect compartments[1].toSet()).first()
            }
            .let { item -> priorities[item]!! }
        }

    // sum of values of items in all 3 strings obtained by splitting the input into groups of 3
    override fun answer2(input: List<String>) =
        input.chunked(3).map{ threeElves ->
            // get first intersecting item in all three elves
            (threeElves[0].toSet() intersect threeElves[1].toSet() intersect threeElves[2].toSet()).first()
                .let{ item -> priorities[item]!! }
        }.sum()
}