package day25

import common.Solution

class Day25: Solution(25) {
    override fun answer1(input: List<String>) = input.map{ Snafu.ofString(it) }.reduce { acc, snafu ->
        acc + snafu
    }.toString()

    override fun answer2(input: List<String>) = -1
}