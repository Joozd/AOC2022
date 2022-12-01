package day01

import common.Solution
import common.extensions.toInts

class Day1: Solution(1) {
    private val elves by lazy { inputFile.split("\n\n") // split input per elf
        .map { it.lines().toInts()  // change elves' string to ints
            .sum()                  // just total weight
        }
    }

    // Most calories carried by an elf
    override fun answer1(): Any = elves.max()

    // Calories carried by the 3 elves that carry the most calories
    override fun answer2(): Any = elves.sortedDescending().take(3).sum()
}