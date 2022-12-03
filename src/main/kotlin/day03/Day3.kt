package day03

import common.Solution

/**
 * Two solutions given.
 * One using intersections which are basically oneliners,
 * and one using manual iteration and selective creation of HashSets which is about 3-4 times faster.
 */
class Day3: Solution(3) {
    private val priorities by lazy{
        HashMap<Char, Int>(52).apply{
            ('a'..'z').forEachIndexed { index, c -> this[c] = index + 1 }
            ('A'..'Z').forEachIndexed { index, c -> this[c] = index + 27}
        }
    }

    override fun answer1(input: List<String>) = answer1Manual(input)
    override fun answer2(input: List<String>) = answer2Manual(input)

    // This implementation takes about 0.086-0.087 ms.
    // Larger sets make is slightly less (0.071) so building the [priorites] map probably takes a relevant amount of time.
    private fun answer1Manual(input: List<String>) = input.sumOf { getValueOfItemInRucksack(it) }

    // This implementation takes either 0.055 or 0.100 ms. Not sure why that sometimes happens.
    private fun answer2Manual(input: List<String>) = input.chunked(3).sumOf{ valueOfCommonItem(it) }

    private fun getValueOfItemInRucksack(rucksack: String): Int{
        val r = rucksack.drop(rucksack.length / 2).toHashSet()
        // Assume all input is not correct and we WILL find an item in first half
        // so don't determine where left half ends
        rucksack.forEach { item ->
            if (item in r)
                return priorities[item]!!
        }
        error("ERROR bad input")
    }

    // elves must be 3 long. Not checked.
    private fun valueOfCommonItem(threeElves: List<String>): Int{
        val two = threeElves[1].toHashSet()
        val three = threeElves[2].toHashSet()
        threeElves[0].forEach{
            if (it in two && it in three)
                return priorities[it]!!
        }
        error("ERROR bad input")
    }

    /***************************************************************************************
     *    Below this are the 'intersect' oneliner solutions. Elon wouldn't like them.      *
     ***************************************************************************************/


    // sum of values of items in both first and second halves of a string
    // This implementation takes about 0.30-0.35ms
    @Suppress("unused")
    private fun answer1Intersect(input: List<String>) =
        input.sumOf { rucksack ->
            rucksack.chunked(rucksack.length / 2).let{ compartments ->
                // get first intersecting item
                (compartments[0].toSet() intersect compartments[1].toSet()).first()
            }
            .let { item -> priorities[item]!! }
        }

    // sum of values of items in all 3 strings obtained by splitting the input into groups of 3
    // This implementation takes about 0.20-0.25ms
    @Suppress("unused")
    private fun answer2Intersect(input: List<String>) =
        // get first intersecting item in all three elves
        input.chunked(3).sumOf { threeElves ->
            // get first intersecting item in all three elves
            (threeElves[0].toSet() intersect threeElves[1].toSet() intersect threeElves[2].toSet()).first()
                .let { item -> priorities[item]!! }
        }
}